package com.shankarkakumani.jaronboardinganimation.feature.landing.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.OnboardingHeader

@Composable
fun LandingScreen(
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF272239))
    ) {
        // Header using the same component as OnboardingScreen
        OnboardingHeader(
            title = "Landing Page",
            iconUrl = "",
            onBackPressed = onBackPressed,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        
        // Centered content
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Landing page",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}