package com.shankarkakumani.jaronboardinganimation.feature.onboarding.state

import androidx.compose.runtime.Stable
import com.shankarkakumani.domain.resource.model.OnboardingDataModel
import com.shankarkakumani.domain.resource.model.EducationCardModel

@Stable
data class OnboardingUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val animationPhase: AnimationPhase = AnimationPhase.Welcome,
    val cards: List<AnimatedCardState> = emptyList(),
    val currentCardIndex: Int = -1,
    val onboardingData: OnboardingDataModel? = null
)

sealed class AnimationPhase {
    object Welcome : AnimationPhase()
    object IntroHiding : AnimationPhase()
    data class CardAnimating(val cardIndex: Int) : AnimationPhase()
    object AllCardsVisible : AnimationPhase()
    object CtaVisible : AnimationPhase()
    object Complete : AnimationPhase()
    object Error : AnimationPhase()
}

@Stable
data class AnimatedCardState(
    val card: EducationCardModel,
    val animationState: CardAnimationState,
    val isVisible: Boolean = false,
    val expandProgress: Float = 0f,
    val translationY: Float = 1000f
)

enum class CardAnimationState {
    HIDDEN,
    SLIDING_UP,
    EXPANDED,
    COLLAPSING,
    COLLAPSED
} 