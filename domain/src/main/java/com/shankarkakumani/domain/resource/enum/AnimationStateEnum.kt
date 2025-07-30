package com.shankarkakumani.domain.resource.enum

enum class AnimationStateEnum {
    IDLE,
    INTRO_SHOWING,
    INTRO_HIDING,
    CARDS_ANIMATING,
    CARDS_VISIBLE,
    CTA_SHOWING,
    COMPLETED,
    ERROR;
    
    fun canTransitionTo(nextState: AnimationStateEnum): Boolean {
        return when (this) {
            IDLE -> nextState == INTRO_SHOWING
            INTRO_SHOWING -> nextState == INTRO_HIDING
            INTRO_HIDING -> nextState == CARDS_ANIMATING
            CARDS_ANIMATING -> nextState in listOf(CARDS_VISIBLE, ERROR)
            CARDS_VISIBLE -> nextState == CTA_SHOWING
            CTA_SHOWING -> nextState == COMPLETED
            COMPLETED -> false
            ERROR -> nextState == INTRO_SHOWING
        }
    }
    
    fun isTerminalState(): Boolean = this in listOf(COMPLETED, ERROR)
} 