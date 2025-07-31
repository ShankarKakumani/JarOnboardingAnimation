package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shankarkakumani.jaronboardinganimation.util.ColorParser

@Composable
fun CtaButton(
    text: String,
    backgroundColor: String,
    textColor: String,
    strokeColor: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    lottieUrl: String? = null // Support for Lottie animation URL from ctaLottie
) {
    if (text.isEmpty()) return
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .background(
                color = ColorParser.parseHexColor(backgroundColor),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = ColorParser.parseHexColor(strokeColor),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // TODO: Add Lottie animation support when dependency is added
        // For now, we use text-only CTA
        // To add Lottie support:
        // 1. Add "com.airbnb.android:lottie-compose:6.0.0" to dependencies
        // 2. Replace Text with LottieAnimation composable when lottieUrl is provided
        // 3. Use: LottieAnimation(lottieUrl, modifier = Modifier.size(24.dp))
        
        Text(
            text = text,
            color = ColorParser.parseHexColor(textColor),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}