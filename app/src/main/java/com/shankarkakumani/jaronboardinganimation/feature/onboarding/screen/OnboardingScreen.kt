package com.shankarkakumani.jaronboardinganimation.feature.onboarding.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.viewmodel.OnboardingViewModel
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.state.OnboardingEvent
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.WelcomeSection
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.LoadingScreen
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.ErrorScreen
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.OnboardingHeader
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.DynamicGradientBackground
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.AnimatedCard

@Composable
fun OnboardingScreen(
    onNavigateToLanding: () -> Unit = {}
) {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toFloat()
    // Background that fills the entire screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Fallback background
    ) {
        // Dynamic background that transitions with card animation
        val animationDuration =
            uiState.onboardingData?.animationConfig?.bottomToCenterTranslationInterval ?: 1500L

        DynamicGradientBackground(
            showWelcome = uiState.showWelcome,
            backgroundColor = uiState.backgroundColor,
            startGradient = uiState.startGradient,
            endGradient = uiState.endGradient,
            startY = uiState.startY,
            animationDuration = animationDuration,
            modifier = Modifier.fillMaxSize()
        )
        // Main content based on state
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }

            uiState.error != null -> {
                ErrorScreen(
                    error = uiState.error!!,
                    onRetry = { viewModel.onEvent(OnboardingEvent.RetryLoad) }
                )
            }

            else -> {
                // Header (always visible) - positioned at top
                AnimatedVisibility(
                    visible = uiState.showWelcome.not(),
                ) {
                    OnboardingHeader(
                        title = "Onboarding",
                        iconUrl = "",
                        onBackPressed = { viewModel.onEvent(OnboardingEvent.OnBackPressed) },
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
                
                // Content area - show welcome or animation content
                AnimatedVisibility(
                    visible = uiState.showWelcome,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300)),
                    modifier = Modifier.fillMaxSize()
                ) {
                    WelcomeSection(
                        title = "Welcome to",
                        subtitle = "Onboarding",
                        onStartAnimation = {
                            viewModel.onEvent(OnboardingEvent.StartOnboarding(screenHeight))
                        }
                    )
                }
                
                // Show animated card when not showing welcome
                uiState.animatedCard?.let { cardState ->
                    AnimatedCard(
                        cardState = cardState,
                    )
                }

                // Show second animated card
                uiState.secondAnimatedCard?.let { cardState ->
                    AnimatedCard(
                        cardState = cardState,
                    )
                }

                // Show third animated card
                uiState.thirdAnimatedCard?.let { cardState ->
                    AnimatedCard(
                        cardState = cardState,
                    )
                }
            }
        }
    }
} 