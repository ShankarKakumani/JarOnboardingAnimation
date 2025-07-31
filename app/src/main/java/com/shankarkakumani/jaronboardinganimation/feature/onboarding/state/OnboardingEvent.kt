package com.shankarkakumani.jaronboardinganimation.feature.onboarding.state

sealed class OnboardingEvent {
    data object OnBackPressed : OnboardingEvent()
    data object RetryLoad : OnboardingEvent()
    data class StartOnboarding(val screenHeight: Float) : OnboardingEvent()
    data object ToggleCardExpansion : OnboardingEvent()
    data object OnCtaClicked : OnboardingEvent()
    data object ResetOnboarding : OnboardingEvent()
} 