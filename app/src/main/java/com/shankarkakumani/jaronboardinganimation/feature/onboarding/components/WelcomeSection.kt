package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun WelcomeSection(
    title: String,
    subtitle: String,
    onStartAnimation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // "Welcome to" text
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFEEEBF5), // Text-Dark-Surface-50
            letterSpacing = 0.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // "Onboarding" text  
        Text(
            text = subtitle,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFFFDD835), // Golden/Yellow color for emphasis
            letterSpacing = 0.sp
        )
        
        // Auto-start onboarding after delay
        LaunchedEffect(title, subtitle) {
            if (title.isNotEmpty() && subtitle.isNotEmpty()) {
                delay(2000) // Show welcome for 2 seconds
                onStartAnimation()
            }
        }
    }
} 