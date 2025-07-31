package com.shankarkakumani.jaronboardinganimation.feature.slideIn.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SlideInSequence(
    modifier: Modifier = Modifier,
    itemCount: Int = 3,
    baseDelay: Long = 200L,
    staggerDelay: Long = 150L
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(itemCount) { index ->
                SlideInItem(
                    startDelay = baseDelay + (index * staggerDelay),
                    index = index
                )
            }
        }
    }
}

@Composable
private fun SlideInItem(
    startDelay: Long,
    index: Int
) {
    var isVisible by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    
    // Calculate the distance so top of items start 100px below bottom of screen
    val itemHeight = 80f // Size of the items in sequence
    val extraBuffer = 300f
    val slideDistance = screenHeight.value + itemHeight + extraBuffer // Start 100px below screen
    
    val translationY by animateFloatAsState(
        targetValue = if (isVisible) 0f else slideDistance,
        animationSpec = tween(durationMillis = 2000),
        label = "sequenceSlideInTranslationY$index"
    )
    

    
    val rotation by animateFloatAsState(
        targetValue = if (isVisible) 0f else 180f,
        animationSpec = tween(durationMillis = 2000),
        label = "sequenceSlideInRotation$index"
    )
    
    LaunchedEffect(Unit) {
        delay(startDelay)
        isVisible = true
    }
    
    val colors = when (index % 3) {
        0 -> listOf(Color(0xFF6200EE), Color(0xFF3700B3))
        1 -> listOf(Color(0xFF03DAC6), Color(0xFF018786))
        else -> listOf(Color(0xFFFF6B6B), Color(0xFFFF5722))
    }
    
    val shape = when (index % 2) {
        0 -> CircleShape
        else -> RoundedCornerShape(16.dp)
    }
    
    Box(
        modifier = Modifier
            .size(80.dp)
            .graphicsLayer(
                translationY = translationY,
                rotationZ = rotation
            )
            .background(
                brush = Brush.verticalGradient(colors),
                shape = shape
            )
    )
}