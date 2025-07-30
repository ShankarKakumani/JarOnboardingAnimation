package com.shankarkakumani.jaronboardinganimation.feature.onboarding.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.viewmodel.OnboardingViewModel
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.OnboardingEvent

@Composable
fun OnboardingScreen(
    onNavigateToLanding: () -> Unit = {}
) {
    // TODO: Add viewModel back when dependencies are resolved
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    // TODO: Implement the complete onboarding screen UI
    // This should include:
    // - Loading state handling
    // - Error state handling 
    // - Welcome section
    // - Animated card container
    // - CTA button
    // - Background gradients
    // - Navigation handling
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Onboarding Screen - TODO: Implement UI")
    }
} 