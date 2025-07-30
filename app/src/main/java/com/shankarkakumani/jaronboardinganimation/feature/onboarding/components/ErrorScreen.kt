package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorScreen(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implement the error screen UI
    // This should include:
    // - Error icon
    // - Error message display
    // - Retry button
    // - Proper styling and layout
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error Screen - TODO: Implement UI")
    }
} 