package com.shankarkakumani.jaronboardinganimation.feature.slideIn.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.ControlledSlideIn
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.CustomSlideInBox
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SimpleSlideInTest
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlideInComponent
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlideInContentType
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlideInSequence
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlidePosition
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlideStageConfig
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.TwoStageSlideIn

@Composable
fun SlideInScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Section 1: Simple Test Slide In
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Simple Slide In Test",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                SimpleSlideInTest(
                    startDelay = 500L,
                    modifier = Modifier.weight(1f, false)
                )
            }
            
            // Section 2: Original Component Test
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Original Slide In",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                SlideInComponent(
                    startDelay = 3000L, // Start after first animation
                    animationDurationMs = 2000,
                    modifier = Modifier.weight(1f, false)
                )
            }
            
            // Section 3: Debug Info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Watch for dramatic slide from bottom!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}