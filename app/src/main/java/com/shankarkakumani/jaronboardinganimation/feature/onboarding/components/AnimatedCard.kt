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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.OrbitalScope
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope
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
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = minOf(328.dp, screenWidth - 32.dp) // Responsive width with padding
    
    // Different animation durations for different phases
    val currentAnimationDuration = when (cardState.animationPhase) {
        AnimationPhase.SLIDE_TO_CENTER -> animationDuration.toInt() // Use API duration for first animation
        AnimationPhase.WAITING -> 0 // No animation during waiting
        AnimationPhase.MOVE_TO_FINAL -> 800 // Faster animation for final positioning
        AnimationPhase.AUTO_COLLAPSE -> 600 // Quick collapse animation using Orbital
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
    
    // Use Orbital for smooth expand/collapse transitions
    Orbital {
        val (cardContent, orbitalScope) = rememberContentWithOrbitalScope {
            if (cardState.isExpanded) {
                ExpandedCardContent(
                    card = cardState.card,
                    orbitalScope = this,
                    sharedElementKey = cardState.sharedElementKey
                )
            } else {
                CollapsedCardContent(
                    card = cardState.card,
                    orbitalScope = this,
                    sharedElementKey = cardState.sharedElementKey,
                    onExpand = onToggleExpansion
                )
            }
        }
        
        Card(
            modifier = modifier
                .width(cardWidth)
                .animateSharedElementTransition(
                    orbitalScope = orbitalScope,
                    sharedElementKey = cardState.sharedElementKey
                )
                .graphicsLayer(
                    translationY = animatedTranslationY,
                    alpha = animatedAlpha
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
            cardContent()
        }
    }
}

@Composable
private fun ExpandedCardContent(
    card: EducationCardModel,
    orbitalScope: OrbitalScope,
    sharedElementKey: String
) {
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
                .height(200.dp)
                .animateSharedElementTransition(
                    orbitalScope = orbitalScope,
                    sharedElementKey = "${sharedElementKey}_image"
                ),
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
                lineHeight = 24.sp,
                modifier = Modifier.animateSharedElementTransition(
                    orbitalScope = orbitalScope,
                    sharedElementKey = "${sharedElementKey}_text"
                )
            )
        }
    }
}

@Composable
private fun CollapsedCardContent(
    card: EducationCardModel,
    orbitalScope: OrbitalScope,
    sharedElementKey: String,
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
                modifier = Modifier
                    .size(32.dp)
                    .animateSharedElementTransition(
                        orbitalScope = orbitalScope,
                        sharedElementKey = "${sharedElementKey}_image"
                    ),
                alignment = Alignment.Center
            )
            
            // Collapsed text next to image
            Text(
                text = card.collapsedStateText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .animateSharedElementTransition(
                        orbitalScope = orbitalScope,
                        sharedElementKey = "${sharedElementKey}_text"
                    ),
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