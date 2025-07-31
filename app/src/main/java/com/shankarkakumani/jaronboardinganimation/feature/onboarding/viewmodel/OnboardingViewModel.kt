package com.shankarkakumani.jaronboardinganimation.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.domain.usecase.onboarding.usecase.GetOnboardingDataUseCase
import com.shankarkakumani.domain.resource.input.GetOnboardingDataInput
import com.shankarkakumani.domain.resource.enum.CacheStrategyEnum
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum
import com.shankarkakumani.domain.resource.model.EducationCardModel
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.*
import com.shankarkakumani.common.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getOnboardingDataUseCase: GetOnboardingDataUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        loadOnboardingData()
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.OnBackPressed -> handleBackPress()
            OnboardingEvent.RetryLoad -> loadOnboardingData()
            is OnboardingEvent.StartOnboarding -> startOnboarding(event.screenHeight)
            OnboardingEvent.ToggleCardExpansion -> toggleCardExpansion()
            OnboardingEvent.OnCtaClicked -> handleCtaClick()
            OnboardingEvent.ResetOnboarding -> resetOnboarding()
        }
    }

    private fun loadOnboardingData() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }

        val input = GetOnboardingDataInput(
            forceRefresh = false,
            cacheStrategy = CacheStrategyEnum.CACHE_FIRST,
            networkClientType = NetworkClientTypeEnum.KTOR
        )

        when (val result = getOnboardingDataUseCase(input)) {
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false, onboardingData = result.data
                    )
                }
            }

            is Result.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.exception.message ?: "Unknown error occurred"
                    )
                }
            }

            is Result.Loading -> {
                _uiState.update {
                    it.copy(
                        isLoading = result.isLoading, error = null
                    )
                }
            }
        }
    }

    private fun startOnboarding(screenHeight: Float) {
        _uiState.update { it.copy(showWelcome = false) }

        // Start card animation
        val firstCard = _uiState.value.onboardingData?.educationCards?.firstOrNull()
        if (firstCard != null) {
            animateFirstCard(firstCard, screenHeight)
        }
    }

    private fun animateFirstCard(card: EducationCardModel, screenHeight: Float) =
        viewModelScope.launch {
            val startPosition = screenHeight
            val centerPosition = 80f + 66f
            val restingPosition = 80f
            
            // Get API-driven animation config with fallbacks
            val animationConfig = _uiState.value.onboardingData?.animationConfig
            val initialDelay = animationConfig?.collapseExpandIntroInterval ?: 500L
            val expandDuration = animationConfig?.expandCardStayInterval ?: 2000L
            val tiltDuration = animationConfig?.collapseCardTiltInterval ?: 2000L

            _uiState.update { state ->
                state.copy(
                    animatedCard = AnimatedCardState(
                        card = card,
                        offset = startPosition,
                        isVisible = true,
                    ),
                    backgroundColor = card.backgroundColor,
                    startGradient = card.startGradient,
                    endGradient = card.endGradient
                )
            }

            // Small delay to ensure card is rendered
            delay(initialDelay)

            // Phase 2: Smoothly move to center position
            _uiState.update { state ->
                state.animatedCard?.let { currentCard ->
                    state.copy(
                        animatedCard = currentCard.copy(offset = centerPosition),
                    )
                } ?: state
            }

            delay(expandDuration)

            _uiState.update { state ->
                state.animatedCard?.let { currentCard ->
                    state.copy(
                        animatedCard = currentCard.copy(
                            isExpanded = false,
                            offset = restingPosition,
                            rotationZ = -6f,
                        ),
                    )
                } ?: state
            }

            // Start second card animation while first card is tilted
            val secondCard = _uiState.value.onboardingData?.educationCards?.getOrNull(1)
            if (secondCard != null) {
                animateSecondCard(secondCard, screenHeight, restingPosition)
            }

            delay(tiltDuration)
            _uiState.update { state ->
                state.animatedCard?.let { currentCard ->
                    state.copy(
                        animatedCard = currentCard.copy(
                            rotationZ = 0f, rotationDuration = 1500
                        ),
                        backgroundColor = null,
                    )
                } ?: state
            }
        }

    private fun animateSecondCard(
        card: EducationCardModel,
        screenHeight: Float,
        previousRestingPosition: Float,
    ) = viewModelScope.launch {
        val startPosition = screenHeight
        val bottomHalf = screenHeight - (444 / 2)
        val restingPosition = previousRestingPosition + 68f + 12f
        
        // Get API-driven animation config with fallbacks
        val animationConfig = _uiState.value.onboardingData?.animationConfig
        val quickDelay = 100L // Small positioning delay
        val expandDuration = animationConfig?.expandCardStayInterval ?: 2000L
        val tiltDuration = animationConfig?.collapseCardTiltInterval ?: 2000L

        _uiState.update { state ->
            state.copy(
                secondAnimatedCard = AnimatedCardState(
                    card = card,
                    offset = startPosition,
                    isVisible = true,
                    rotationDuration = 1500,
                ),
            )
        }

        delay(quickDelay)

        _uiState.update { state ->
            state.secondAnimatedCard?.let { currentCard ->
                state.copy(
                    secondAnimatedCard = currentCard.copy(
                        offset = bottomHalf,
                        rotationZ = +6f,
                    ),
                    backgroundColor = null,
                )
            } ?: state
        }

        delay(expandDuration)

        _uiState.update { state ->
            state.secondAnimatedCard?.let { currentCard ->
                state.copy(
                    secondAnimatedCard = currentCard.copy(
                        offset = restingPosition,
                        rotationZ = 0f,
                        rotationDuration = 1500,
                    ),
                    backgroundColor = card.backgroundColor,
                    startGradient = card.startGradient,
                    endGradient = card.endGradient
                )
            } ?: state
        }

        delay(tiltDuration)

        _uiState.update { state ->
            state.secondAnimatedCard?.let { currentCard ->
                state.copy(
                    secondAnimatedCard = currentCard.copy(
                        isExpanded = false, offset = restingPosition,
                        rotationZ = +6f
                    )
                )
            } ?: state
        }

        // Start third card animation while second card is tilted
        val thirdCard = _uiState.value.onboardingData?.educationCards?.getOrNull(2)
        if (thirdCard != null) {
            animateThirdCard(thirdCard, screenHeight, restingPosition)
        }

        delay(2000)

        _uiState.update { state ->
            state.secondAnimatedCard?.let { currentCard ->
                state.copy(
                    secondAnimatedCard = currentCard.copy(
                        rotationZ = 0f,  // Straighten the card back to 0°
                        rotationDuration = 1500  // Control the duration from ViewModel
                    )
                )
            } ?: state
        }
    }

    private fun animateThirdCard(
        card: EducationCardModel, screenHeight: Float, previousRestingPosition: Float
    ) = viewModelScope.launch {
        val startPosition = screenHeight
        val bottomHalf = screenHeight - (444 / 2)
        val restingPosition = previousRestingPosition + 68f + 12f
        
        // Get API-driven animation config with fallbacks
        val animationConfig = _uiState.value.onboardingData?.animationConfig
        val quickDelay = 100L // Small positioning delay
        val expandDuration = animationConfig?.expandCardStayInterval ?: 2000L

        _uiState.update { state ->
            state.copy(
                thirdAnimatedCard = AnimatedCardState(
                    card = card,
                    offset = startPosition,
                    isVisible = true,
                    rotationZ = -6f,
                    rotationDuration = 1500,
                ),
            )
        }

        delay(quickDelay)

        _uiState.update { state ->
            state.thirdAnimatedCard?.let { currentCard ->
                state.copy(
                    thirdAnimatedCard = currentCard.copy(
                        offset = bottomHalf,
                    ),
                    backgroundColor = null,
                )
            } ?: state
        }

        delay(expandDuration)

        _uiState.update { state ->
            state.thirdAnimatedCard?.let { currentCard ->
                state.copy(
                    thirdAnimatedCard = currentCard.copy(
                        offset = restingPosition,
                        rotationZ = 0f,
                        rotationDuration = 1500,
                    ),
                    backgroundColor = card.backgroundColor,
                    startGradient = card.startGradient,
                    endGradient = card.endGradient
                )
            } ?: state
        }

        delay(expandDuration)
        _uiState.update { state ->
            state.thirdAnimatedCard?.let { currentCard ->
                state.copy(
                    backgroundColor = null,
                    showFinalCTA = true
                )
            } ?: state
        }
    }

    private fun handleBackPress() {
        _uiState.update {
            it.copy(
                showWelcome = true,
                animatedCard = null,
                secondAnimatedCard = null,
                thirdAnimatedCard = null,
                showFinalCTA = false,
            )
        }
    }
    
    private fun handleCtaClick() {
        // No state change needed - navigation handles transition
    }
    
    private fun resetOnboarding() {
        _uiState.update {
            OnboardingUiState(
                isLoading = false,
                error = null,
                showWelcome = true,
                onboardingData = it.onboardingData,
                animatedCard = null,
                secondAnimatedCard = null,
                thirdAnimatedCard = null,
                startGradient = null,
                endGradient = null,
                backgroundColor = null,
                startY = 0f,
                showFinalCTA = false
            )
        }
    }

    private fun updateGradientStartY(newStartY: Float) {
        _uiState.update {
            it.copy(startY = newStartY)
        }
    }

    private fun toggleCardExpansion() {
        _uiState.update { state ->
            state.animatedCard?.let { currentCard ->
                state.copy(
                    animatedCard = currentCard.copy(isExpanded = !currentCard.isExpanded)
                )
            } ?: state
        }
    }
} 