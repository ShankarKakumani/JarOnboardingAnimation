package com.shankarkakumani.jaronboardinganimation.feature.slideIn.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class SlideStage {
    HIDDEN,
    HALF_VISIBLE,
    FULLY_VISIBLE
}

@Composable
fun TwoStageSlideIn(
    modifier: Modifier = Modifier,
    startDelay: Long = 500L,
    stage1Duration: Int = 1000, // Time to reach half position
    pauseDuration: Long = 2000L, // Pause time at half position
    stage2Duration: Int = 1000, // Time to reach final position
    content: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
) {
    var slideStage by remember { mutableStateOf(SlideStage.HIDDEN) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    
    // Calculate positions - Fixed Logic
    val cardHeight = 350f
    val extraBuffer = 300f
    val startPosition = screenHeight.value + cardHeight + extraBuffer // Start way below
    val halfPosition = (screenHeight.value / 2) - (cardHeight / 2) // Center of card at bottom of screen (half visible)
    val finalPosition = 0f // Center of screen
    
    // Calculate current target position based on stage
    val targetPosition = when (slideStage) {
        SlideStage.HIDDEN -> startPosition
        SlideStage.HALF_VISIBLE -> halfPosition
        SlideStage.FULLY_VISIBLE -> finalPosition
    }
    
    // Current animation duration based on which stage we're in
    val currentDuration = when (slideStage) {
        SlideStage.HIDDEN -> 0 // No animation yet
        SlideStage.HALF_VISIBLE -> stage1Duration
        SlideStage.FULLY_VISIBLE -> stage2Duration
    }
    
    val translationY by animateFloatAsState(
        targetValue = targetPosition,
        animationSpec = tween(durationMillis = currentDuration),
        label = "twoStageSlideInTranslationY"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (slideStage == SlideStage.HIDDEN) 0f else 1f,
        animationSpec = tween(durationMillis = stage1Duration / 2),
        label = "twoStageSlideInAlpha"
    )
    
    // Animation sequence control
    LaunchedEffect(Unit) {
        delay(startDelay)
        
        // Stage 1: Slide to half position
        slideStage = SlideStage.HALF_VISIBLE
        delay(stage1Duration.toLong())
        
        // Pause at half position
        delay(pauseDuration)
        
        // Stage 2: Slide to final position
        slideStage = SlideStage.FULLY_VISIBLE
    }
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(
                    translationY = translationY,
                    alpha = alpha
                )
        ) {
            content()
        }
    }
}