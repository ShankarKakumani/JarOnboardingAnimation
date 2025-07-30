package com.shankarkakumani.jaronboardinganimation.ui.animations

import com.shankarkakumani.domain.resource.model.AnimationConfigModel
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimatedCardState
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimationPhase
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.CardAnimationState
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardAnimator @Inject constructor() {
    
    suspend fun startSequentialAnimation(
        cards: List<AnimatedCardState>,
        config: AnimationConfigModel,
        onPhaseChange: (AnimationPhase) -> Unit,
        onCardStateChange: (cardIndex: Int, state: CardAnimationState) -> Unit
    ) {
        // Hide intro
        onPhaseChange(AnimationPhase.IntroHiding)
        delay(config.collapseExpandIntroInterval.toLong())
        
        // Animate each card sequentially
        cards.forEachIndexed { index, _ ->
            onPhaseChange(AnimationPhase.CardAnimating(index))
            animateCard(index, config, onCardStateChange)
        }
        
        // All cards visible
        onPhaseChange(AnimationPhase.AllCardsVisible)
        delay(500)
        
        // Show CTA
        onPhaseChange(AnimationPhase.CtaVisible)
    }
    
    private suspend fun animateCard(
        cardIndex: Int,
        config: AnimationConfigModel,
        onStateChange: (cardIndex: Int, state: CardAnimationState) -> Unit
    ) {
        // Slide up
        onStateChange(cardIndex, CardAnimationState.SLIDING_UP)
        delay(config.bottomToCenterTranslationInterval.toLong())
        
        // Expand
        onStateChange(cardIndex, CardAnimationState.EXPANDED)
        delay(config.expandCardStayInterval.toLong())
        
        // Collapse
        onStateChange(cardIndex, CardAnimationState.COLLAPSING)
        delay(config.collapseCardTiltInterval.toLong())
        
        // Final collapsed state
        onStateChange(cardIndex, CardAnimationState.COLLAPSED)
        delay(200) // Brief pause before next card
    }
} 