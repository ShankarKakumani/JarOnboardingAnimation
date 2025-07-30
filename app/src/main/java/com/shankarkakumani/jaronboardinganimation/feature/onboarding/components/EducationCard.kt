package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimatedCardState

@Composable
fun EducationCard(
    cardState: AnimatedCardState,
    animationProgress: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Implement the education card UI
    // This should include:
    // - Dynamic height based on animation state
    // - Background gradients from card data
    // - Stroke borders with gradients
    // - Card image (AsyncImage)
    // - Text content (expanded vs collapsed)
    // - Dropdown indicator icon
    // - Click handling
    // - Animation transitions
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Education Card - TODO: Implement UI")
        }
    }
} 