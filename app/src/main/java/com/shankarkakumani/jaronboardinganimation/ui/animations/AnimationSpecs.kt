package com.shankarkakumani.jaronboardinganimation.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

object AnimationSpecs {
    val cardSlideIn = spring<Dp>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
    
    val cardExpand = spring<Dp>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    val fadeInOut = tween<Float>(
        durationMillis = 300,
        easing = LinearOutSlowInEasing
    )
    
    val backgroundTransition = tween<Color>(
        durationMillis = 800,
        easing = FastOutSlowInEasing
    )
    
    val cardTranslationY = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessMedium
    )
    
    val ctaButtonSlideIn = spring<Dp>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )
} 