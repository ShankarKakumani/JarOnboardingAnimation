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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

data class SlideStageConfig(
    val position: SlidePosition,
    val duration: Int, // Animation duration to reach this stage
    val pauseAfter: Long = 0L // How long to pause at this stage
)

enum class SlidePosition {
    HIDDEN, // Way below screen
    BOTTOM_EDGE, // Bottom of card at bottom of screen
    HALF_VISIBLE, // Center of card at bottom of screen  
    QUARTER_UP, // 25% of the way to center
    THREE_QUARTER_UP, // 75% of the way to center
    CENTER // Final center position
}

@Composable
fun ControlledSlideIn(
    modifier: Modifier = Modifier,
    startDelay: Long = 500L,
    stages: List<SlideStageConfig> = listOf(
        SlideStageConfig(SlidePosition.HALF_VISIBLE, duration = 1000, pauseAfter = 2000L),
        SlideStageConfig(SlidePosition.CENTER, duration = 1000)
    ),
    content: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6200EE),
                            Color(0xFF03DAC6),
                            Color(0xFFFF6B6B)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        )
    }
) {
    var currentStageIndex by remember { mutableStateOf(-1) } // -1 means not started
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    
    // Calculate all positions
    val cardHeight = 350f
    val extraBuffer = 300f
    val screenHeightPx = screenHeight.value
    
    fun getPositionValue(position: SlidePosition): Float {
        return when (position) {
            SlidePosition.HIDDEN -> screenHeightPx + cardHeight + extraBuffer // Start way below screen
            SlidePosition.BOTTOM_EDGE -> (screenHeightPx / 2) + (cardHeight / 2) // Bottom of card at bottom of screen
            SlidePosition.HALF_VISIBLE -> (screenHeightPx / 2) - (cardHeight / 2) // Center of card at bottom of screen (half visible)
            SlidePosition.QUARTER_UP -> ((screenHeightPx / 2) - (cardHeight / 2)) * 0.75f // 25% of the way from half to center
            SlidePosition.THREE_QUARTER_UP -> ((screenHeightPx / 2) - (cardHeight / 2)) * 0.25f // 75% of the way from half to center  
            SlidePosition.CENTER -> 0f // Final center position
        }
    }
    
    // Get current target position
    val targetPosition = if (currentStageIndex >= 0 && currentStageIndex < stages.size) {
        getPositionValue(stages[currentStageIndex].position)
    } else {
        getPositionValue(SlidePosition.HIDDEN)
    }
    
    // Get current animation duration
    val currentDuration = if (currentStageIndex >= 0 && currentStageIndex < stages.size) {
        stages[currentStageIndex].duration
    } else {
        0
    }
    
    val translationY by animateFloatAsState(
        targetValue = targetPosition,
        animationSpec = tween(durationMillis = currentDuration),
        label = "controlledSlideInTranslationY"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (currentStageIndex >= 0) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "controlledSlideInAlpha"
    )
    
    // Animation sequence control
    LaunchedEffect(Unit) {
        delay(startDelay)
        
        for (i in stages.indices) {
            currentStageIndex = i
            delay(stages[i].duration.toLong())
            
            // Pause at this stage if configured
            if (stages[i].pauseAfter > 0) {
                delay(stages[i].pauseAfter)
            }
        }
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