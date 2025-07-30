package com.shankarkakumani.jaronboardinganimation.feature.slideIn.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

sealed class SlideInContentType {
    data class ColorBox(
        val colors: List<Color>,
        val shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(16.dp)
    ) : SlideInContentType()
    
    data class ImageContent(
        val painter: Painter,
        val contentDescription: String? = null
    ) : SlideInContentType()
    
    data class CustomContent(
        val content: @Composable () -> Unit
    ) : SlideInContentType()
}

@Composable
fun CustomSlideInBox(
    modifier: Modifier = Modifier,
    startDelay: Long = 300L,
    animationDurationMs: Int = 2000,
    contentType: SlideInContentType = SlideInContentType.ColorBox(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary
        )
    )
) {
    var isVisible by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    
    // Calculate the distance so top of card starts 100px below bottom of screen
    val cardHeight = 350f
    val extraBuffer = 300f
    val slideDistance = screenHeight.value + cardHeight + extraBuffer // Start 100px below screen
    
    val translationY by animateFloatAsState(
        targetValue = if (isVisible) 0f else slideDistance,
        animationSpec = tween(durationMillis = animationDurationMs),
        label = "customSlideInTranslationY"
    )
    

    
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = animationDurationMs / 2), // Fade in faster
        label = "customSlideInAlpha"
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
        ) {
            when (contentType) {
                is SlideInContentType.ColorBox -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(contentType.colors),
                                shape = contentType.shape
                            )
                    )
                }
                is SlideInContentType.ImageContent -> {
                    Image(
                        painter = contentType.painter,
                        contentDescription = contentType.contentDescription,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
                is SlideInContentType.CustomContent -> {
                    contentType.content()
                }
            }
        }
    }
}