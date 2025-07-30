package com.shankarkakumani.jaronboardinganimation.feature.slideIn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.state.SlideInEvent
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.state.SlideInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideInViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(SlideInUiState())
    val uiState: StateFlow<SlideInUiState> = _uiState.asStateFlow()
    
    fun onEvent(event: SlideInEvent) {
        when (event) {
            is SlideInEvent.StartAnimation -> startAnimation()
            is SlideInEvent.ResetAnimation -> resetAnimation()
            is SlideInEvent.PauseAnimation -> pauseAnimation()
            is SlideInEvent.ResumeAnimation -> resumeAnimation()
            is SlideInEvent.SetAnimationDelay -> setAnimationDelay(event.delay)
            is SlideInEvent.SetSlideDistance -> setSlideDistance(event.distance)
        }
    }
    
    private fun startAnimation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isAnimating = true,
                currentStep = 0
            )
            
            // Simulate animation steps
            repeat(_uiState.value.totalSteps) { step ->
                if (!_uiState.value.isPaused) {
                    delay(_uiState.value.animationDelay)
                    _uiState.value = _uiState.value.copy(currentStep = step + 1)
                }
            }
            
            _uiState.value = _uiState.value.copy(isAnimating = false)
        }
    }
    
    private fun resetAnimation() {
        _uiState.value = _uiState.value.copy(
            isAnimating = false,
            isPaused = false,
            currentStep = 0
        )
    }
    
    private fun pauseAnimation() {
        _uiState.value = _uiState.value.copy(isPaused = true)
    }
    
    private fun resumeAnimation() {
        _uiState.value = _uiState.value.copy(isPaused = false)
    }
    
    private fun setAnimationDelay(delay: Long) {
        _uiState.value = _uiState.value.copy(animationDelay = delay)
    }
    
    private fun setSlideDistance(distance: Float) {
        _uiState.value = _uiState.value.copy(slideDistance = distance)
    }
}