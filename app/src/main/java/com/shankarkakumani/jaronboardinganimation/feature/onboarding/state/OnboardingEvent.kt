package com.shankarkakumani.jaronboardinganimation.feature.onboarding.state

sealed class OnboardingEvent {
    object StartAnimation : OnboardingEvent()
    object SkipAnimation : OnboardingEvent()
    object PauseAnimation : OnboardingEvent()
    object ResumeAnimation : OnboardingEvent()
    object OnCtaClick : OnboardingEvent()
    object OnBackPressed : OnboardingEvent()
    object RetryLoad : OnboardingEvent()
    data class OnCardClick(val cardIndex: Int) : OnboardingEvent()
} 