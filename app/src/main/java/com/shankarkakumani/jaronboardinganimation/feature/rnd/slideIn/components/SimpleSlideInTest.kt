package com.shankarkakumani.jaronboardinganimation.feature.slideIn.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SimpleSlideInTest(
    modifier: Modifier = Modifier,
    startDelay: Long = 500L
) {
    var isVisible by remember { mutableStateOf(false) }
    
    // Start with top of card 100px below bottom of screen
    val cardHeight = 350f
    val extraBuffer = 300f
    val translationY by animateFloatAsState(
        targetValue = if (isVisible) 0f else (1500f + cardHeight + extraBuffer), // Start 100px below screen
        animationSpec = tween(durationMillis = 2000),
        label = "testSlideInTranslationY"
    )
    

    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "testSlideInAlpha"
    )
    
    LaunchedEffect(Unit) {
        delay(startDelay)
        isVisible = true
    }
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        )
    }
}