package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    // TODO: Implement the loading screen UI
    // This should include:
    // - Circular progress indicator
    // - Proper centering
    // - Material design styling
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading Screen - TODO: Implement UI")
    }
} 