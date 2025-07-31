package com.shankarkakumani.jaronboardinganimation.feature.slideIn.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.ControlledSlideIn
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.DebugControlledSlideIn
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlidePosition
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlideStageConfig
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.TwoStageSlideIn
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.VisualDebugSlideIn

@Composable
fun ControlledSlideScreen(
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
//            // Section 1: Two-Stage Slide In
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(
//                    text = "Two-Stage Animation",
//                    style = MaterialTheme.typography.headlineSmall,
//                    fontWeight = FontWeight.Bold,
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//                Text(
//                    text = "Slide → Pause 2s → Continue",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
//                )
//
//                TwoStageSlideIn(
//                    startDelay = 500L,
//                    stage1Duration = 1000,
//                    pauseDuration = 2000L,
//                    stage2Duration = 1000,
//                    modifier = Modifier.weight(1f, false)
//                )
//            }
            
            // Section 2: Debug Controlled Animation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Debug Multi-Stage Control",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Shows position calculations in real-time",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                
                DebugControlledSlideIn(
                    startDelay = 0L, // Start immediately
                    modifier = Modifier.weight(1f, false)
                )
            }
        
        }
    }
}