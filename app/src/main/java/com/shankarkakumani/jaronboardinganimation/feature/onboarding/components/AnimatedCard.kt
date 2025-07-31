package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimatedCardState
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimationPhase
import com.shankarkakumani.domain.resource.model.EducationCardModel
import androidx.core.graphics.toColorInt
import com.shankarkakumani.jaronboardinganimation.ui.theme.StyleManager


/**
 * AnimatedCard using absoluteOffset for position control
 * 
 * Benefits of using absoluteOffset over translationY:
 * 1. Direct dp-based positioning - no pixel conversion needed
 * 2. More intuitive coordinate system - works directly with screen dimensions
 * 3. Better control over positioning relative to screen bounds
 * 4. Easier to reason about animations in dp units
 * 5. Can animate smoothly just like translationY with animateFloatAsState
 */
@Composable
fun AnimatedCard(
    cardState: AnimatedCardState,
    animationDuration: Long = 1500L,
    onToggleExpansion: () -> Unit = {},
) {
    val configuration = LocalConfiguration.current

    val absoluteOffset by animateFloatAsState(
        targetValue = cardState.offset - 100,
        animationSpec = tween(
            durationMillis = 1000,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "cardOffsetY"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (cardState.isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "cardAlpha"
    )

    val animatedRotationZ by animateFloatAsState(
        targetValue = cardState.rotationZ,
        animationSpec = tween(
            durationMillis = cardState.rotationDuration,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "cardRotation"
    )

    val transformOrigin = when {
        cardState.rotationZ > 0 -> TransformOrigin(
            0f,
            0.5f
        )
        cardState.rotationZ < 0 -> TransformOrigin(
            1f,
            0.5f
        )
        else -> TransformOrigin(0.5f, 0.5f) // No rotation: center origin
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .absoluteOffset(y = (absoluteOffset).dp)
            .rotate(animatedRotationZ)
            .graphicsLayer(
                alpha = animatedAlpha,
                transformOrigin = transformOrigin
            )

    ) {
        if (cardState.isExpanded) {
            ExpandedCardContent(card = cardState.card)
        } else {
            CollapsedCardContent(card = cardState.card, onExpand = onToggleExpansion)
        }
    }

}

@Composable
private fun ExpandedCardContent(card: EducationCardModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(444.dp)
            .background(
                color = parseHexColor(card.backgroundColor).copy(alpha = 0.3f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Real WebP image from API
        AsyncImage(
            model = card.image,
            contentDescription = card.expandStateText,
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillBounds
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = card.expandStateText,
            style = StyleManager.h2.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CollapsedCardContent(
    card: EducationCardModel,
    onExpand: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        parseHexColor(card.backgroundColor),
                        parseHexColor(card.startGradient)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onExpand() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Small image on left
            AsyncImage(
                model = card.image,
                contentDescription = card.collapsedStateText,
                modifier = Modifier.size(32.dp),
                alignment = Alignment.Center
            )

            // Collapsed text next to image
            Text(
                text = card.collapsedStateText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 12.dp),
                maxLines = 2
            )
        }
    }
}

/**
 * Parse hex color string to Compose Color
 */
private fun parseHexColor(hexString: String): Color {
    return try {
        val colorString = if (hexString.startsWith("#")) hexString else "#$hexString"
        Color(colorString.toColorInt())
    } catch (e: Exception) {
        Color.Gray // Fallback color
    }
}