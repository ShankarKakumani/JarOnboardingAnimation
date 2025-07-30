package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shankarkakumani.jaronboardinganimation.R

@Composable
fun DynamicGradientBackground(
    showWelcome: Boolean,
    cardBackgroundColor: String? = null,
    animationDuration: Long = 1500L,
    modifier: Modifier = Modifier
) {
    // Welcome background color
    val welcomeColor = Color(0xFF322B47)
    
    // Animate transition progress
    val animationProgress by animateFloatAsState(
        targetValue = if (showWelcome) 0f else 1f,
        animationSpec = tween(durationMillis = animationDuration.toInt()),
        label = "backgroundTransition"
    )
    
    // Calculate current background using exact Figma colors
    val currentBrush = if (showWelcome) {
        // Solid welcome color
        Brush.verticalGradient(
            colors = listOf(welcomeColor, welcomeColor)
        )
    } else {
        // Use card's backgroundColor if available, otherwise use Figma colors
        val baseColor = if (cardBackgroundColor != null) {
            parseHexColor(cardBackgroundColor)
        } else {
            Color(0xFF992D7E) // Fallback to Figma color
        }
        
        // Exact Figma gradient specifications:
        // 1. linear-gradient(180deg, rgba(83, 81, 83, 0.2) 0%, rgba(baseColor, 0.8) 100%)
        // 2. linear-gradient(0deg, rgba(baseColor, 0.4), rgba(baseColor, 0.4))
        
        // First gradient colors (180deg - top to bottom)
        val gradientTopColor = Color(0xFF535353).copy(alpha = 0.2f)      // rgba(83, 81, 83, 0.2) 
        val gradientBottomColor = baseColor.copy(alpha = 0.8f)           // Use card color with 80% opacity
        
        // Second gradient overlay color (0deg - uniform overlay)
        val overlayColor = baseColor.copy(alpha = 0.4f)                  // Use card color with 40% opacity
        
        // Interpolate from welcome color to the combined gradient effect
        val interpolatedTop = lerp(welcomeColor, gradientTopColor, animationProgress)
        val interpolatedBottom = lerp(welcomeColor, gradientBottomColor, animationProgress)
        
        // Create the primary vertical gradient (180deg)
        Brush.verticalGradient(
            colors = listOf(interpolatedTop, interpolatedBottom),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
        )
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = currentBrush)
    ) {
        // Second gradient overlay (0deg - uniform overlay) to match Figma exactly
        if (!showWelcome) {
            val baseColor = if (cardBackgroundColor != null) {
                parseHexColor(cardBackgroundColor)
            } else {
                Color(0xFF992D7E) // Fallback to Figma color
            }
            val overlayColor = baseColor.copy(alpha = 0.4f * animationProgress)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = overlayColor)
            )
        }
        
        // Radial gradient overlay at bottom (above background, below UI) - Always visible
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = !showWelcome,
            enter = fadeIn()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_radial_gradient),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}

/**
 * Parse hex color string to Compose Color
 */
private fun parseHexColor(hexString: String): Color {
    return try {
        val colorString = if (hexString.startsWith("#")) hexString else "#$hexString"
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: Exception) {
        Color.Gray // Fallback color
    }
} 