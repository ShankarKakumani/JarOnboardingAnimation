package com.shankarkakumani.jaronboardinganimation.feature.onboarding.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.runtime.LaunchedEffect
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
import com.shankarkakumani.jaronboardinganimation.feature.onboarding.components.CtaButton

@Composable
fun OnboardingScreen(
    onNavigateToLanding: () -> Unit = {}
) {
    val viewModel: OnboardingViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.onEvent(OnboardingEvent.ResetOnboarding)
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.toFloat()
    
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
                
                uiState.animatedCard?.let { cardState ->
                    AnimatedCard(
                        cardState = cardState,
                        animationConfig = uiState.onboardingData?.animationConfig
                    )
                }

                uiState.secondAnimatedCard?.let { cardState ->
                    AnimatedCard(
                        cardState = cardState,
                        animationConfig = uiState.onboardingData?.animationConfig
                    )
                }

                uiState.thirdAnimatedCard?.let { cardState ->
                    AnimatedCard(
                        cardState = cardState,
                        animationConfig = uiState.onboardingData?.animationConfig
                    )
                }

                AnimatedVisibility(
                    visible = uiState.showFinalCTA,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    uiState.onboardingData?.let { data ->
                        CtaButton(
                            text = data.saveButton.text,
                            backgroundColor = data.saveButton.backgroundColor,
                            textColor = data.saveButton.textColor,
                            strokeColor = data.saveButton.strokeColor,
                            lottieUrl = data.ctaLottie,
                            onClick = {
                                viewModel.onEvent(OnboardingEvent.OnCtaClicked)
                                onNavigateToLanding()
                            },
                            modifier = Modifier.navigationBarsPadding().padding(bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
} 