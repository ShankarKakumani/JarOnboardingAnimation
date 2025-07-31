package com.shankarkakumani.jaronboardinganimation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shankarkakumani.jaronboardinganimation.feature.landing.screen.LandingScreen
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
            LandingScreen(
                onBackPressed = {
                    navController.navigate(NavigationDestinations.ONBOARDING) {
                        popUpTo(NavigationDestinations.LANDING) { inclusive = true }
                    }
                }
            )
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

 