package com.shankarkakumani.jaronboardinganimation.feature.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shankarkakumani.domain.resource.model.EducationCardModel

@Composable
fun EducationCard(
    card: EducationCardModel,
    isExpanded: Boolean = true,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val cardWidth = minOf(328.dp, screenWidth - 32.dp) // Responsive width with padding
    val cardHeight = if (isExpanded) 444.dp else 68.dp
    
    Card(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            parseHexColor(card.backgroundColor),
                            parseHexColor(card.startGradient)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isExpanded) {
                ExpandedCardContent(card)
            } else {
                CollapsedCardContent(card)
            }
        }
    }
}

@Composable
private fun ExpandedCardContent(card: EducationCardModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
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
private fun CollapsedCardContent(card: EducationCardModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Icon and text in horizontal layout
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Real image from API as icon
            AsyncImage(
                model = card.image,
                contentDescription = card.collapsedStateText,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            )
            
            // Text content from API
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = card.collapsedStateText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    maxLines = 1
                )
            }
            
            // Dropdown arrow
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "⌄",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
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