package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimatedCardState
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.AnimationPhase
import com.shankarkakumani.domain.resource.model.EducationCardModel


@Composable
fun AnimatedCard(
    cardState: AnimatedCardState,
    animationDuration: Long = 1500L,
    onToggleExpansion: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current

    // Different animation durations for different phases
    val currentAnimationDuration = when (cardState.animationPhase) {
        AnimationPhase.SLIDE_TO_CENTER -> animationDuration.toInt() // Use API duration for first animation
        AnimationPhase.WAITING -> 0 // No animation during waiting
        AnimationPhase.MOVE_TO_FINAL -> 800 // COMMENTED OUT: Faster animation for final positioning
        AnimationPhase.AUTO_COLLAPSE -> 600 // Resize animation at center position
    }
    
    // Smooth animation for translation with controlled timing (no overshoot)
    val animatedTranslationY by animateFloatAsState(
        targetValue = cardState.translationY,
        animationSpec = tween(
            durationMillis = currentAnimationDuration,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "cardTranslationY"
    )
    
    // Alpha animation for smooth visibility transition
    val animatedAlpha by animateFloatAsState(
        targetValue = if (cardState.isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "cardAlpha"
    )
    
    // Height animation for expand/collapse
    val animatedHeight by animateFloatAsState(
        targetValue = if (cardState.isExpanded) 444f else 68f,
        animationSpec = tween(
            durationMillis = if (cardState.animationPhase == AnimationPhase.AUTO_COLLAPSE) 600 else currentAnimationDuration,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "cardHeight"
    )

    
    val animatedRotationZ by animateFloatAsState(
        targetValue = cardState.rotationZ,
        animationSpec = tween(
            durationMillis = cardState.rotationDuration,
            easing = androidx.compose.animation.core.FastOutSlowInEasing
        ),
        label = "cardRotation"
    )
    
    // Calculate translation adjustment to keep top edge fixed during height changes
    val expandedHeight = 444f
    val collapsedHeight = 68f
    val heightDifference = expandedHeight - animatedHeight
    
    // When shrinking, move the card up by half the height difference
    // This keeps the top edge in the same position while bottom edge moves up
    val topAnchorAdjustment = when {
        !cardState.isExpanded -> {
            heightDifference / 2f // Move up by half the shrunk amount to keep top edge fixed
        }
        else -> 0f // When expanded, no adjustment needed
    }
    
    // Determine transform origin based on rotation direction
    val transformOrigin = when {
        cardState.rotationZ > 0 -> TransformOrigin(0f, 0.5f) // Positive rotation: pin left side, right side goes down
        cardState.rotationZ < 0 -> TransformOrigin(1f, 0.5f) // Negative rotation: pin right side, left side goes down
        else -> TransformOrigin(0.5f, 0.5f) // No rotation: center origin
    }
    
    // Standard Compose animation for expand/collapse transitions
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(animatedHeight.dp)
            .graphicsLayer(
                translationY = animatedTranslationY + topAnchorAdjustment,
                alpha = animatedAlpha,
                rotationZ = animatedRotationZ,
                transformOrigin = transformOrigin
            )
            .clickable { onToggleExpansion() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(444.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        parseHexColor(card.backgroundColor),
                        parseHexColor(card.startGradient)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Real WebP image from API
        AsyncImage(
            model = card.image,
            contentDescription = card.expandStateText,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            alignment = Alignment.Center
        )
        
        // Card text at bottom from API
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 240.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = card.expandStateText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }
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
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: Exception) {
        Color.Gray // Fallback color
    }
}