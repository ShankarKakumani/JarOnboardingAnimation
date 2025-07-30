package com.shankarkakumani.jaronboardinganimation.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimationPhase

object ComposeUtils {
    
    fun getBackgroundGradient(animationPhase: AnimationPhase): List<Color> {
        return when (animationPhase) {
            AnimationPhase.Welcome -> listOf(
                Color(0xFF1A1A2E),
                Color(0xFF16213E)
            )
            AnimationPhase.IntroHiding,
            is AnimationPhase.CardAnimating,
            AnimationPhase.AllCardsVisible,
            AnimationPhase.CtaVisible -> listOf(
                Color(0xFF0F3460),
                Color(0xFF16213E)
            )
            AnimationPhase.Complete -> listOf(
                Color(0xFF16213E),
                Color(0xFF1A1A2E)
            )
            AnimationPhase.Error -> listOf(
                Color(0xFF2D1B1B),
                Color(0xFF1A1A2E)
            )
        }
    }
    
    fun createVerticalGradientBrush(colors: List<Color>): Brush {
        return Brush.verticalGradient(colors = colors)
    }
} 