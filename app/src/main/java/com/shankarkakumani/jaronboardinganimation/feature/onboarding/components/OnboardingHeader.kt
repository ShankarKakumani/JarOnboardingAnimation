package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingHeader(
    title: String,
    iconUrl: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implement the onboarding header UI
    // This should include:
    // - Back button
    // - Title text
    // - Icon from URL (AsyncImage)
    // - Proper layout and styling
    // - Status bar padding
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Onboarding Header - TODO: Implement UI")
    }
} 