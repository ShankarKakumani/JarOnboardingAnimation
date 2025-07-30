package com.shankarkakumani.jaronboardinganimation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.screen.OnboardingScreen
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.screen.ControlledSlideScreen
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.screen.CustomPatternScreen
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.screen.SlideInScreen
import com.shankarkakumani.jaronboardinganimation.feature.slideIn.screen.SlidePlaygroundScreen

@Composable
fun OnboardingNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestinations.ONBOARDING,
        modifier = modifier
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
        
        composable(NavigationDestinations.SLIDE_PLAYGROUND) {
            SlidePlaygroundScreen(
                onNavigateToBasicSlide = {
                    navController.navigate(NavigationDestinations.SLIDE_IN_BASIC)
                },
                onNavigateToControlledSlide = {
                    navController.navigate(NavigationDestinations.SLIDE_IN_CONTROLLED)
                },
                onNavigateToCustomPattern = {
                    navController.navigate(NavigationDestinations.SLIDE_IN_CUSTOM_PATTERN)
                }
            )
        }
        
        composable(NavigationDestinations.SLIDE_IN_BASIC) {
            SlideInScreen()
        }
        
        composable(NavigationDestinations.SLIDE_IN_CONTROLLED) {
            ControlledSlideScreen()
        }
        
        composable(NavigationDestinations.SLIDE_IN_CUSTOM_PATTERN) {
            CustomPatternScreen()
        }
    }
}

@Composable
private fun LandingPlaceholderScreen() {
    // TODO: Replace with actual landing screen implementation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(text = "Landing Screen - TODO: Implement")
    }
} 