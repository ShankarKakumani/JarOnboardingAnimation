package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.shankarkakumani.jaronboardinganimation.util.ColorParser

@Composable
fun CtaButton(
    text: String,
    backgroundColor: String,
    textColor: String,
    strokeColor: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    lottieUrl: String? = null
) {
    if (text.isEmpty()) return
    
    Row(
        modifier = modifier
            .height(56.dp)
            .wrapContentWidth()
            .background(
                color = ColorParser.parseHexColor(backgroundColor),
                shape = RoundedCornerShape(31.dp)
            )
            .border(
                width = 1.dp,
                color = ColorParser.parseHexColor(strokeColor),
                shape = RoundedCornerShape(31.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.width(32.dp))
        Text(
            text = text,
            color = ColorParser.parseHexColor(textColor),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        // Lottie animation if URL is provided
        if (!lottieUrl.isNullOrBlank()) {
            val compositionResult = rememberLottieComposition(LottieCompositionSpec.Url(lottieUrl))
            LottieAnimation(
                composition = compositionResult.value,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(44.dp)
                    .graphicsLayer(rotationZ = -180f)
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
    }
}