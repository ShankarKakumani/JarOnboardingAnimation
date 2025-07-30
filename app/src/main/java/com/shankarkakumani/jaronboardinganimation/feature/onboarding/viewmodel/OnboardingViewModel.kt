package com.shankarkakumani.jaronboardinganimation.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.domain.usecase.onboarding.usecase.GetOnboardingDataUseCase
import com.shankarkakumani.domain.resource.input.GetOnboardingDataInput
import com.shankarkakumani.domain.resource.enum.CacheStrategyEnum
import com.shankarkakumani.domain.resource.enum.NetworkClientTypeEnum
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.*
import com.shankarkakumani.jaronboardinganimation.ui.animations.CardAnimator
import com.shankarkakumani.common.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getOnboardingDataUseCase: GetOnboardingDataUseCase,
    private val cardAnimator: CardAnimator
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private var animationJob: Job? = null

    init {
        loadOnboardingData()
    }

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            OnboardingEvent.StartAnimation -> startAnimation()
            OnboardingEvent.SkipAnimation -> skipToEnd()
            OnboardingEvent.PauseAnimation -> pauseAnimation()
            OnboardingEvent.ResumeAnimation -> resumeAnimation()
            OnboardingEvent.OnCtaClick -> handleCtaClick()
            OnboardingEvent.OnBackPressed -> handleBackPress()
            OnboardingEvent.RetryLoad -> loadOnboardingData()
            is OnboardingEvent.OnCardClick -> handleCardClick(event.cardIndex)
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
                val onboardingData = result.data
                val cards = onboardingData.educationCards.mapIndexed { index, card ->
                    AnimatedCardState(
                        card = card,
                        animationState = CardAnimationState.HIDDEN
                    )
                }
                
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        cards = cards,
                        onboardingData = onboardingData
                    )
                }
            }
            is Result.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.exception.message ?: "Unknown error occurred",
                        animationPhase = AnimationPhase.Error
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

    private fun startAnimation() {
        animationJob?.cancel()
        animationJob = viewModelScope.launch {
            val animationConfig = _uiState.value.onboardingData?.animationConfig ?: return@launch
            
            cardAnimator.startSequentialAnimation(
                cards = _uiState.value.cards,
                config = animationConfig,
                onPhaseChange = { phase ->
                    _uiState.update { it.copy(animationPhase = phase) }
                },
                onCardStateChange = { cardIndex, cardState ->
                    _uiState.update { state ->
                        state.copy(
                            cards = state.cards.mapIndexed { index, card ->
                                if (index == cardIndex) card.copy(animationState = cardState)
                                else card
                            },
                            currentCardIndex = cardIndex
                        )
                    }
                }
            )
        }
    }

    private fun skipToEnd() {
        animationJob?.cancel()
        _uiState.update { state ->
            state.copy(
                animationPhase = AnimationPhase.CtaVisible,
                cards = state.cards.map { card ->
                    card.copy(
                        animationState = CardAnimationState.COLLAPSED,
                        isVisible = true,
                        expandProgress = 0f,
                        translationY = 0f
                    )
                }
            )
        }
    }

    private fun pauseAnimation() {
        animationJob?.cancel()
    }

    private fun resumeAnimation() {
        startAnimation()
    }

    private fun handleCtaClick() {
        // Navigate to next screen or handle CTA action
        _uiState.update { it.copy(animationPhase = AnimationPhase.Complete) }
    }

    private fun handleBackPress() {
        // Handle back navigation
        when (_uiState.value.animationPhase) {
            is AnimationPhase.Welcome -> {
                // Allow system back behavior
            }
            else -> {
                // Reset to welcome state
                _uiState.update { it.copy(animationPhase = AnimationPhase.Welcome) }
            }
        }
    }

    private fun handleCardClick(cardIndex: Int) {
        val currentCards = _uiState.value.cards
        if (cardIndex in currentCards.indices) {
            val clickedCard = currentCards[cardIndex]
            val newState = when (clickedCard.animationState) {
                CardAnimationState.COLLAPSED -> CardAnimationState.EXPANDED
                CardAnimationState.EXPANDED -> CardAnimationState.COLLAPSED
                else -> return // Don't handle clicks during animation
            }
            
            _uiState.update { state ->
                state.copy(
                    cards = state.cards.mapIndexed { index, card ->
                        if (index == cardIndex) card.copy(animationState = newState)
                        else card
                    }
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        animationJob?.cancel()
    }
} 