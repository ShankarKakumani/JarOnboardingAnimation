package com.shankarkakumani.jaronboardinganimation.feature.slideIn.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

@Composable
fun DebugControlledSlideIn(
    modifier: Modifier = Modifier,
    startDelay: Long = 500L
) {
    var currentStageIndex by remember { mutableStateOf(-1) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    
    // Calculate all positions with debug info
    val cardHeight = 350f
    val extraBuffer = 300f
    val screenHeightPx = screenHeight.value
    
    // Debug positions - Fixed Logic
    val hiddenPos = screenHeightPx + cardHeight + extraBuffer
    val bottomEdgePos = (screenHeightPx / 2) + (cardHeight / 2)  // Bottom of card at screen bottom
    val halfVisiblePos = screenHeightPx + (cardHeight / 2) + extraBuffer  // Center of card at screen bottom
    val quarterUpPos = halfVisiblePos * 0.75f  // 25% of the way from half to center
    val centerPos = 0f
    
    val stages = listOf(
        "HALF_VISIBLE" to halfVisiblePos,
        "QUARTER_UP" to quarterUpPos,
        "CENTER" to centerPos
    )
    
    val currentStage = if (currentStageIndex >= 0 && currentStageIndex < stages.size) {
        stages[currentStageIndex]
    } else {
        "HIDDEN" to hiddenPos
    }
    
    val translationY by animateFloatAsState(
        targetValue = currentStage.second,
        animationSpec = tween(durationMillis = 1000),
        label = "debugSlideInTranslationY"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (currentStageIndex >= 0) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "debugSlideInAlpha"
    )
    
    LaunchedEffect(Unit) {
        delay(startDelay)
        
        for (i in stages.indices) {
            currentStageIndex = i
            delay(2000L) // 2 seconds at each stage
        }
    }
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Debug info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "Screen Height: ${screenHeightPx.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "Card Height: ${cardHeight.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "Current Stage: ${currentStage.first}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "Translation Y: ${translationY.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "Expected positions:",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "• HALF_VISIBLE: ${halfVisiblePos.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "• QUARTER_UP: ${quarterUpPos.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
            Text(
                text = "• CENTER: ${centerPos.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
        
        // Animated card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .graphicsLayer(
                    translationY = translationY,
                    alpha = alpha
                )
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
}