package com.shankarkakumani.jaronboardinganimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.os.Build
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
                
                // Configure system bars - ensure black navigation bar
                SideEffect {
                    val window = this@MainActivity.window
                    val insetsController = WindowCompat.getInsetsController(window, window.decorView)
                    
                    // Force navigation bar to black (works on all API levels)
                    window.navigationBarColor = android.graphics.Color.BLACK
                    
                    // Configure icon colors for dark theme
                    insetsController.apply {
                        isAppearanceLightStatusBars = false // white icons on dark background
                        isAppearanceLightNavigationBars = false // white icons on dark background
                    }
                }
                
                // Content with proper system bar padding
                OnboardingNavigation(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}