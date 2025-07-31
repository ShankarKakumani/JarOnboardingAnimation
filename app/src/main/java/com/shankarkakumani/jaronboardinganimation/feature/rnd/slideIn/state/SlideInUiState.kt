package com.shankarkakumani.jaronboardinganimation.feature.slideIn.state

data class SlideInUiState(
    val isAnimating: Boolean = false,
    val isPaused: Boolean = false,
    val animationDelay: Long = 300L,
    val slideDistance: Float = 400f,
    val currentStep: Int = 0,
    val totalSteps: Int = 3
) {
    val isComplete: Boolean
        get() = currentStep >= totalSteps && !isAnimating
}