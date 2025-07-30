package com.shankarkakumani.jaronboardinganimation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.screen.OnboardingScreen

@Composable
fun OnboardingNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.ONBOARDING
    ) {
        composable(NavigationDestinations.ONBOARDING) {
            OnboardingScreen(
                onNavigateToLanding = {
                    navController.navigate(NavigationDestinations.LANDING) {
                        popUpTo(NavigationDestinations.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        
        composable(NavigationDestinations.LANDING) {
            // TODO: Implement or connect your landing screen here
            LandingPlaceholderScreen()
        }
    }
}

@Composable
private fun LandingPlaceholderScreen() {
    // TODO: Replace with actual landing screen implementation
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(text = "Landing Screen - TODO: Implement")
    }
} 