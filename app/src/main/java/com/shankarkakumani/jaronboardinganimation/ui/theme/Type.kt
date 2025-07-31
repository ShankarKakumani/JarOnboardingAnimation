package com.shankarkakumani.jaronboardinganimation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
// import androidx.compose.ui.text.font.Font
// import com.shankarkakumani.jaronboardinganimation.R

// TODO: Add Inter font files to res/font/ directory and uncomment this:
/*
val InterFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_medium, FontWeight.Medium)
)
*/

// StyleManager for clean typography access
object StyleManager {
    val h1 = TextStyle(
        fontFamily = FontFamily.Default, // TODO: Replace with InterFontFamily when font files are added
        fontWeight = FontWeight.Bold, // 700 weight = Bold
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp, // 0% letter spacing
        textAlign = TextAlign.Center
    )
    
    val h2 = TextStyle(
        fontFamily = FontFamily.Default, // TODO: Replace with InterFontFamily when font files are added
        fontWeight = FontWeight.Bold, // 700 weight = Bold
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp, // 0% letter spacing
        textAlign = TextAlign.Center
    )
    
    val h3 = TextStyle(
        fontFamily = FontFamily.Default, // TODO: Replace with InterFontFamily when font files are added
        fontWeight = FontWeight.Bold, // 700 weight = Bold
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp, // 0% letter spacing
        textAlign = TextAlign.Start
    )
    
    val h4 = TextStyle(
        fontFamily = FontFamily.Default, // TODO: Replace with InterFontFamily when font files are added
        fontWeight = FontWeight.Bold, // 700 weight = Bold
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp, // 0% letter spacing
        textAlign = TextAlign.Center
    )
}

// Clean Material Typography - use defaults
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)