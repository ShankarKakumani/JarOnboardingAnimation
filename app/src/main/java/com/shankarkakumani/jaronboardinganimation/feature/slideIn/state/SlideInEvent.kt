package com.shankarkakumani.jaronboardinganimation.feature.slideIn.state

sealed class SlideInEvent {
    object StartAnimation : SlideInEvent()
    object ResetAnimation : SlideInEvent()
    object PauseAnimation : SlideInEvent()
    object ResumeAnimation : SlideInEvent()
    data class SetAnimationDelay(val delay: Long) : SlideInEvent()
    data class SetSlideDistance(val distance: Float) : SlideInEvent()
}