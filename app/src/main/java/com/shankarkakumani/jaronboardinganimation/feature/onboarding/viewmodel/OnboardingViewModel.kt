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
        }
    }

    private fun loadOnboardingData() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }

        val input = GetOnboardingDataInput(
            forceRefresh = false,
            cacheStrategy = CacheStrategyEnum.CACHE_FIRST,
            networkClientType = NetworkClientTypeEnum.RETROFIT
        )

        when (val result = getOnboardingDataUseCase(input)) {
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        onboardingData = result.data
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
                        isLoading = result.isLoading,
                        error = null
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
            // Calculate positions like in slideIn PoC - using actual screen dimensions
            val cardHeight = 444f
            val extraBuffer = 300f

            // Start position: way below screen (like PoC)
            val startPosition = screenHeight + cardHeight + extraBuffer

            // First target position: 178px from top (center position)
            // Since we're using Alignment.Center, center = screenHeight/2
            // 178px from top = center - (screenHeight/2 - 178)
            val centerPosition = -(screenHeight / 2 - 178f) // 178px from top

            // Final target position: 12px from top
            val finalPosition = -(screenHeight / 2 - 12f) // 12px from top

            // Debug logging
            println("🔍 Card Animation Debug:")
            println("   Screen Height: $screenHeight")
            println("   Start Position: $startPosition (should be below screen)")
            println("   Center Position: $centerPosition (should be 178px from top)")
            println("   Final Position: $finalPosition (should be 12px from top)")

            // Phase 1: Initialize card at bottom of screen (way below)
            _uiState.update { state ->
                state.copy(
                    animatedCard = AnimatedCardState(
                        card = card,
                        translationY = startPosition,
                        isVisible = true,
                        animationPhase = AnimationPhase.SLIDE_TO_CENTER
                    )
                )
            }

            // Small delay to ensure card is rendered
            delay(50)

            // Phase 2: Smoothly move to center position (178px from top)
            _uiState.update { state ->
                state.animatedCard?.let { currentCard ->
                    state.copy(
                        animatedCard = currentCard.copy(translationY = centerPosition)
                    )
                } ?: state
            }

            // Phase 3: Wait for first animation to complete + expandCardStayInterval
            val firstAnimationDuration =
                _uiState.value.onboardingData?.animationConfig?.bottomToCenterTranslationInterval
                    ?: 1500L
            val stayInterval =
                _uiState.value.onboardingData?.animationConfig?.expandCardStayInterval ?: 2000L

            delay(firstAnimationDuration + stayInterval)

            // Start second card animation while first card is tilted
            val secondCard = _uiState.value.onboardingData?.educationCards?.getOrNull(1)
            if (secondCard != null) {
                animateSecondCard(secondCard, screenHeight)
            }
            _uiState.update { state ->
                state.animatedCard?.let { currentCard ->
                    state.copy(
                        animatedCard = currentCard.copy(
                            isExpanded = false,
                            animationPhase = AnimationPhase.AUTO_COLLAPSE,
                            translationY = -screenHeight - 300f,
                            rotationZ = -6f  // Tilt 6° - left side down, right side up
                        )
                    )
                } ?: state
            }


            // Wait for 2 seconds, then straighten the card over 1.5 seconds
            delay(2000)

            _uiState.update { state ->
                state.animatedCard?.let { currentCard ->
                    state.copy(
                        animatedCard = currentCard.copy(
                            rotationZ = 0f,  // Straighten the card back to 0°
                            rotationDuration = 1500  // Control the duration from ViewModel
                        )
                    )
                } ?: state
            }
        }

    private fun animateSecondCard(card: EducationCardModel, screenHeight: Float) =
        viewModelScope.launch {
            // Calculate positions exactly like first card
            val cardHeight = 800f
            val extraBuffer = 300f

            // Start position: way below screen (like first card)
            val startPosition = screenHeight + cardHeight + extraBuffer

            // Target position: 178px from top (center position)
            val bottomHalf = screenHeight + (800f / 2)
            val centerPosition = 0f

            // Debug logging
            println("🔍 Second Card Animation Debug:")
            println("   Screen Height: $screenHeight")
            println("   Start Position: $startPosition (should be below screen)")
            println("   Center Position: $centerPosition (should be 178px from top)")

            // Phase 1: Initialize second card at bottom of screen (way below)
            _uiState.update { state ->
                state.copy(
                    secondAnimatedCard = AnimatedCardState(
                        card = card,
                        translationY = startPosition,
                        isVisible = true,
                        rotationZ = +6f,
                        rotationDuration = 1500,
                    )
                )
            }

            // Small delay to ensure card is rendered
            delay(100)

            _uiState.update { state ->
                state.secondAnimatedCard?.let { currentCard ->
                    state.copy(
                        secondAnimatedCard = currentCard.copy(
                            translationY = bottomHalf,
                            rotationDuration = 1500,
                        )
                    )
                } ?: state
            }

            delay(2000)

            _uiState.update { state ->
                state.secondAnimatedCard?.let { currentCard ->
                    state.copy(
                        secondAnimatedCard = currentCard.copy(
                            translationY = centerPosition,
                            rotationZ = 0f,
                            rotationDuration = 1500,
                        )
                    )
                } ?: state
            }
            delay(2000)

            _uiState.update { state ->
                state.secondAnimatedCard?.let { currentCard ->
                    state.copy(
                        secondAnimatedCard = currentCard.copy(
                            isExpanded = false,
                            animationPhase = AnimationPhase.AUTO_COLLAPSE,
                            translationY = -screenHeight - 0f,
                            rotationZ = +6f  // Tilt 6° - left side pinned, right side down
                        )
                    )
                } ?: state
            }
        }

    private fun handleBackPress() {
        // Handle back navigation - reset to welcome and clear animated card
        _uiState.update { it.copy(showWelcome = true, animatedCard = null) }
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