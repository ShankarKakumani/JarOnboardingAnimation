package com.shankarkakumani.jaronboardinganimation.feature.slideIn.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun VisualDebugSlideIn(
    modifier: Modifier = Modifier,
    startDelay: Long = 500L
) {
    var currentStageIndex by remember { mutableStateOf(-1) }
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenHeight = configuration.screenHeightDp.dp
    
    // Calculate all positions with debug info
    val cardHeight = 350f
    val extraBuffer = 300f
    val screenHeightPx = screenHeight.value
    
    // OLD calculation (center above bottom line)
    val oldHalfVisiblePos = (screenHeightPx / 2) - (cardHeight / 2)  // = 434 - 175 = 259
    
    // ACTUAL CORRECT calculation - BOTTOM of card at screen bottom (red line)
    // We want only TOP HALF of card visible, so bottom of card = screen bottom
    // This means card center should be cardHeight/2 above the screen bottom
    // In Android coordinate system: translationY = (screenHeight/2) - (cardHeight/2)
    val actualCorrectPos = (screenHeightPx + (cardHeight/2) + extraBuffer) 
    
    // Test: center of card at screen bottom (this was wrong)
    val centerAtBottomPos = screenHeightPx / 2  // 434px
    
    val stages = listOf(
        "WRONG (center at red)" to centerAtBottomPos,
        "CORRECT (bottom at red)" to actualCorrectPos,
        "CENTER" to 0f
    )
    
    val currentStage = if (currentStageIndex >= 0 && currentStageIndex < stages.size) {
        stages[currentStageIndex]
    } else {
        "HIDDEN" to (screenHeightPx + cardHeight + extraBuffer)
    }
    
    val translationY by animateFloatAsState(
        targetValue = currentStage.second,
        animationSpec = tween(durationMillis = 2000),
        label = "visualDebugSlideInTranslationY"
    )
    
    val alpha by animateFloatAsState(
        targetValue = if (currentStageIndex >= 0) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "visualDebugSlideInAlpha"
    )
    
    LaunchedEffect(Unit) {
        delay(startDelay)
        
        for (i in stages.indices) {
            currentStageIndex = i
            delay(3000L) // 3 seconds at each stage to observe
        }
    }
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Debug lines showing screen boundaries
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    // Draw line at screen center (where translationY = 0 positions the view center)
                    drawLine(
                        color = Color.Yellow,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 4.dp.toPx()
                    )
                    // Draw line at screen bottom
                    drawLine(
                        color = Color.Red,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 4.dp.toPx()
                    )
                }
        )
        
        // Debug info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart)
        ) {
            Text(
                text = "Visual Debug - Coordinate System",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = "Yellow Line = Screen Center (translationY = 0)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Yellow
            )
            Text(
                text = "Red Line = Screen Bottom",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red
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
                text = "Wrong (center at red) = ${centerAtBottomPos.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red
            )
            Text(
                text = "Correct (bottom at red) = ${actualCorrectPos.toInt()}px",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Green
            )
        }
        
        // Animated card with center marker
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .graphicsLayer(
                    translationY = translationY,
                    alpha = alpha
                )
        ) {
            // Main card
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
            
            // Center marker - shows exact center of the card
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(4.dp)
                    .background(Color.White)
                    .align(Alignment.Center)
            )
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(20.dp)
                    .background(Color.White)
                    .align(Alignment.Center)
            )
        }
    }
}