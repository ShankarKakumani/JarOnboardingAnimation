package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
import com.shankarkakumani.domain.resource.model.EducationCardModel
import com.shankarkakumani.domain.resource.model.AnimationConfigModel
import com.shankarkakumani.jaronboardinganimation.ui.theme.StyleManager
import com.shankarkakumani.jaronboardinganimation.util.ColorParser
import androidx.compose.runtime.remember


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
    animationConfig: AnimationConfigModel? = null,
) {
    val configuration = LocalConfiguration.current

    // Use API-driven animation config with fallbacks
    val slideAnimationDuration = remember(animationConfig) {
        animationConfig?.bottomToCenterTranslationInterval?.toInt() ?: 1000
    }
    
    val fadeAnimationDuration = remember(animationConfig) {
        animationConfig?.collapseExpandIntroInterval?.toInt() ?: 300
    }

    val absoluteOffset by animateFloatAsState(
        targetValue = cardState.offset,
        animationSpec = tween(
            durationMillis = slideAnimationDuration,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "cardOffsetY"
    )

    val animatedAlpha by animateFloatAsState(
        targetValue = if (cardState.isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = fadeAnimationDuration),
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
        animatedRotationZ > 0.1f -> TransformOrigin(0f, 0.5f) // Left edge for positive rotation
        animatedRotationZ < -0.1f -> TransformOrigin(1f, 0.5f) // Right edge for negative rotation
        cardState.rotationZ > 0.1f -> TransformOrigin(
            0f,
            0.5f
        ) // Maintain left edge when straightening from positive
        cardState.rotationZ < -0.1f -> TransformOrigin(
            1f,
            0.5f
        ) // Maintain right edge when straightening from negative
        else -> TransformOrigin(0.5f, 0.5f) // Center origin only if never rotated
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentHeight()
            .absoluteOffset(y = absoluteOffset.dp)
            .graphicsLayer(
                alpha = animatedAlpha,
                rotationZ = animatedRotationZ,
                transformOrigin = transformOrigin
            )

    ) {
        if (cardState.isExpanded) {
            ExpandedCardContent(card = cardState.card)
        } else {
            CollapsedCardContent(card = cardState.card)
        }
    }

}

@Composable
private fun ExpandedCardContent(card: EducationCardModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .height(444.dp)
            .background(
                color = ColorParser.parseHexColor(card.backgroundColor).copy(alpha = 0.3f),
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 0.5.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        ColorParser.parseHexColor(card.strokeStartColor).copy(alpha = 0.2f),
                        ColorParser.parseHexColor(card.strokeEndColor)
                    ),

                ),
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
            style = StyleManager.h4.copy(color = Color.White),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CollapsedCardContent(
    card: EducationCardModel,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(
                color = ColorParser.parseHexColor(card.backgroundColor).copy(alpha = 0.32f),
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Small image on left
            Box(modifier = Modifier.background(shape = CircleShape, color = Color.Transparent)) {
                AsyncImage(
                    model = card.image,
                    contentDescription = card.collapsedStateText,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    alignment = Alignment.Center
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Collapsed text next to image
            Text(
                text = card.collapsedStateText,
                style = StyleManager.h6.copy(color = Color.White),
            )
        }
    }
}

