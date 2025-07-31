package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.shankarkakumani.jaronboardinganimation.R
import com.shankarkakumani.jaronboardinganimation.util.ColorParser

@Composable
fun DynamicGradientBackground(
    showWelcome: Boolean,
    backgroundColor: String? = null,
    startGradient: String? = null,
    endGradient: String? = null,
    animationDuration: Long = 1500L,
    startY: Float = 0f,
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
    
    // Animate shimmer colors with smooth transitions
    val colorTransitionDuration = 800 // Smooth color transition duration
    
    val animatedShimmerStartColor by animateColorAsState(
        targetValue = ColorParser.parseHexColorOrDefault(startGradient, Color.Transparent),
        animationSpec = tween(durationMillis = colorTransitionDuration),
        label = "shimmerStartColor"
    )
    
    val animatedShimmerEndColor by animateColorAsState(
        targetValue = ColorParser.parseHexColorOrDefault(endGradient, Color.Transparent),
        animationSpec = tween(durationMillis = colorTransitionDuration),
        label = "shimmerEndColor"
    )
    
    val animatedBackgroundColor by animateColorAsState(
        targetValue = ColorParser.parseHexColorOrDefault(backgroundColor, Color.Transparent),
        animationSpec = tween(durationMillis = colorTransitionDuration),
        label = "backgroundColor"
    )
    
    // Animate startY position for gradient control
    val animatedStartY by animateFloatAsState(
        targetValue = startY,
        animationSpec = tween(durationMillis = colorTransitionDuration),
        label = "gradientStartY"
    )
    
    // Calculate current background using exact Figma colors
    val currentBrush = if (showWelcome) {
        // Solid welcome color
        Brush.verticalGradient(
            colors = listOf(welcomeColor, welcomeColor)
        )
    } else {
        // Use animated shimmer colors with smooth transitions
        val gradientTopColor = animatedShimmerStartColor.copy(alpha = 0.16f)
        val gradientBottomColor = animatedShimmerEndColor.copy(alpha = 1f)
        // Create the primary vertical gradient (180deg)
        Brush.verticalGradient(
            colors = listOf(gradientTopColor, gradientBottomColor),
            startY = -500f,
            endY = Float.POSITIVE_INFINITY
        )
    }


    Box(
        modifier = Modifier.background(Color(0xFF1E1E1E))
    ) {
        Box(modifier = Modifier.background(Color(0xFF04602A).copy(alpha = 0.16f))) {


            if (!showWelcome) {
                val overlayColor = animatedBackgroundColor.copy(alpha = 0.4f)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = overlayColor)
                )
            }

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(brush = currentBrush)
            )

            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight(),
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


}

 