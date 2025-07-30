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
    
    private fun animateFirstCard(card: EducationCardModel, screenHeight: Float) = viewModelScope.launch {
        // Calculate positions like in slideIn PoC - using actual screen dimensions
        val cardHeight = 444f
        val extraBuffer = 300f
        
        // Start position: way below screen (like PoC)
        val startPosition = screenHeight + cardHeight + extraBuffer
        
        // First target position: 178px from top (center position)
        // Since we're using Alignment.Center, center = screenHeight/2
        // 178px from top = center - (screenHeight/2 - 178)
        val centerPosition = -(screenHeight/2 - 178f) // 178px from top
        
        // Final target position: 12px from top
        val finalPosition = -(screenHeight/2 - 12f) // 12px from top
        
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
        val firstAnimationDuration = _uiState.value.onboardingData?.animationConfig?.bottomToCenterTranslationInterval ?: 1500L
        val stayInterval = _uiState.value.onboardingData?.animationConfig?.expandCardStayInterval ?: 2000L
        
        delay(firstAnimationDuration + stayInterval)
        
        // Phase 4: Update animation phase to WAITING
        _uiState.update { state ->
            state.animatedCard?.let { currentCard ->
                state.copy(
                    animatedCard = currentCard.copy(animationPhase = AnimationPhase.WAITING)
                )
            } ?: state
        }
        
        // Phase 5: Move to final position (12px from top)
        _uiState.update { state ->
            state.animatedCard?.let { currentCard ->
                state.copy(
                    animatedCard = currentCard.copy(
                        translationY = finalPosition,
                        animationPhase = AnimationPhase.MOVE_TO_FINAL
                    )
                )
            } ?: state
        }
        
        // Phase 6: Wait for the move-to-final animation to complete, then auto-collapse
        delay(800) // Duration of move-to-final animation
        
        // Phase 7: Auto-collapse to small size using Orbital
        _uiState.update { state ->
            state.animatedCard?.let { currentCard ->
                state.copy(
                    animatedCard = currentCard.copy(
                        isExpanded = false,
                        animationPhase = AnimationPhase.AUTO_COLLAPSE
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