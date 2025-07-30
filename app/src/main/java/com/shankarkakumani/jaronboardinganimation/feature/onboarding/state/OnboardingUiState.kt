package com.shankarkakumani.jaronboardinganimation.feature.onboarding.state

import androidx.compose.runtime.Stable
import com.shankarkakumani.domain.resource.model.OnboardingDataModel
import com.shankarkakumani.domain.resource.model.EducationCardModel

@Stable
data class OnboardingUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val showWelcome: Boolean = true,
    val onboardingData: OnboardingDataModel? = null,
    val animatedCard: AnimatedCardState? = null,
    val secondAnimatedCard: AnimatedCardState? = null
)

@Stable
data class AnimatedCardState(
    val card: EducationCardModel,
    val translationY: Float = 1000f, // Start below screen
    val isVisible: Boolean = false,
    val animationPhase: AnimationPhase = AnimationPhase.SLIDE_TO_CENTER,
    val isExpanded: Boolean = true, // Start expanded, can collapse to small size
    val rotationZ: Float = 0f, // Rotation in degrees
    val rotationDuration: Int = 1500, // Duration for straightening rotation
    val sharedElementKey: String = "educationCard" // For Orbital transitions
)

enum class AnimationPhase {
    SLIDE_TO_CENTER,    // First animation: slide from bottom to center (178px from top)
    WAITING,            // Waiting for expandCardStayInterval
    MOVE_TO_FINAL,      // Second animation: move to final position (12px from top)
    AUTO_COLLAPSE       // Third animation: automatically collapse to small size
} 