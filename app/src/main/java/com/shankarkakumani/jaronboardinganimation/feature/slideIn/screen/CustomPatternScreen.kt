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
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlidePosition
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.components.SlideStageConfig

@Composable
fun CustomPatternScreen(
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
            // Pattern 1: Fast → Slow → Ultra Slow
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pattern 1: Speed Variations",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Fast → Slow → Ultra Slow",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                
                ControlledSlideIn(
                    startDelay = 500L,
                    stages = listOf(
                        SlideStageConfig(
                            position = SlidePosition.THREE_QUARTER_UP,
                            duration = 300, // Fast
                            pauseAfter = 500L
                        ),
                        SlideStageConfig(
                            position = SlidePosition.QUARTER_UP,
                            duration = 1500, // Slow
                            pauseAfter = 800L
                        ),
                        SlideStageConfig(
                            position = SlidePosition.CENTER,
                            duration = 3000 // Ultra slow
                        )
                    ),
                    modifier = Modifier.weight(1f, false)
                )
            }
            
            // Pattern 2: Bounce Effect
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pattern 2: Bounce Effect",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Overshoot → Back → Center",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                
                ControlledSlideIn(
                    startDelay = 6000L, // Start after first animation completes
                    stages = listOf(
                        SlideStageConfig(
                            position = SlidePosition.THREE_QUARTER_UP,
                            duration = 800,
                            pauseAfter = 300L
                        ),
                        SlideStageConfig(
                            position = SlidePosition.QUARTER_UP, // Go back down
                            duration = 400,
                            pauseAfter = 200L
                        ),
                        SlideStageConfig(
                            position = SlidePosition.CENTER,
                            duration = 600
                        )
                    ),
                    modifier = Modifier.weight(1f, false)
                )
            }
            
            // Pattern 3: Stutter Effect
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pattern 3: Stutter Effect",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Quick → Pause → Quick → Pause → Final",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                
                ControlledSlideIn(
                    startDelay = 12000L, // Start after previous animations
                    stages = listOf(
                        SlideStageConfig(
                            position = SlidePosition.HALF_VISIBLE,
                            duration = 200, // Quick
                            pauseAfter = 1000L // Long pause
                        ),
                        SlideStageConfig(
                            position = SlidePosition.QUARTER_UP,
                            duration = 200, // Quick
                            pauseAfter = 1000L // Long pause
                        ),
                        SlideStageConfig(
                            position = SlidePosition.CENTER,
                            duration = 400 // Final smooth
                        )
                    ),
                    modifier = Modifier.weight(1f, false)
                )
            }
        }
    }
}