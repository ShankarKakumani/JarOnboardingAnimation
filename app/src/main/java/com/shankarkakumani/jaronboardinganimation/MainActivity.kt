package com.shankarkakumani.jaronboardinganimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.shankarkakumani.jaronboardinganimation.navigation.OnboardingNavigation
import com.shankarkakumani.jaronboardinganimation.ui.theme.JarOnboardingAnimationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JarOnboardingAnimationTheme {
                val navController = rememberNavController()
                
                // Configure system bars to use light content (white icons) and black background
                SideEffect {
                    val window = this@MainActivity.window
                    WindowCompat.getInsetsController(window, window.decorView).apply {
                        isAppearanceLightStatusBars = false // false = light content (white icons)
                        isAppearanceLightNavigationBars = false // false = light content for nav bar
                    }
                }
                
                // Content with proper system bar padding
                OnboardingNavigation(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding() // Add padding for navigation bar
                )
            }
        }
    }
}