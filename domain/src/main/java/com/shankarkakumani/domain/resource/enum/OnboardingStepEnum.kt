package com.shankarkakumani.domain.resource.enum

enum class OnboardingStepEnum {
    WELCOME,
    CARD_ANIMATION,
    CALL_TO_ACTION,
    COMPLETED;
    
    fun getProgress(): Float {
        return when (this) {
            WELCOME -> 0.25f
            CARD_ANIMATION -> 0.5f
            CALL_TO_ACTION -> 0.75f
            COMPLETED -> 1.0f
        }
    }
    
    fun next(): OnboardingStepEnum? {
        return when (this) {
            WELCOME -> CARD_ANIMATION
            CARD_ANIMATION -> CALL_TO_ACTION
            CALL_TO_ACTION -> COMPLETED
            COMPLETED -> null
        }
    }
} 