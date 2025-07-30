package com.shankarkakumani.data.mapper

import com.shankarkakumani.data.dto.*
import com.shankarkakumani.domain.resource.model.*

// DTO to Domain Model mappings
fun OnboardingApiResponseDto.toOnboardingDataModel(): OnboardingDataModel {
    return OnboardingDataModel(
        introTitle = data.onboardingScreen.introTitle,
        introSubtitle = data.onboardingScreen.introSubtitle,
        ctaLottie = data.onboardingScreen.ctaLottie,
        educationCards = data.onboardingScreen.educationCards.map { it.toEducationCardModel() },
        saveButton = data.onboardingScreen.saveButton.toSaveButtonModel(),
        toolBarIcon = data.onboardingScreen.toolBarIcon,
        toolBarText = data.onboardingScreen.toolBarText,
        animationConfig = data.onboardingScreen.toAnimationConfigModel()
    )
}

fun EducationCardDataDto.toEducationCardModel(): EducationCardModel {
    return EducationCardModel(
        image = image,
        endGradient = endGradient,
        startGradient = startGradient,
        strokeEndColor = strokeEndColor,
        backgroundColor = backgroundColor,
        expandStateText = expandStateText,
        strokeStartColor = strokeStartColor,
        collapsedStateText = collapsedStateText
    )
}

fun SaveButtonDataDto.toSaveButtonModel(): SaveButtonModel {
    return SaveButtonModel(
        text = text,
        textColor = textColor,
        strokeColor = strokeColor,
        backgroundColor = backgroundColor,
        icon = icon,
        deeplink = deeplink
    )
}

fun OnboardingScreenDataDto.toAnimationConfigModel(): AnimationConfigModel {
    return AnimationConfigModel(
        expandCardStayInterval = expandCardStayInterval.toLong(),
        collapseCardTiltInterval = collapseCardTiltInterval.toLong(),
        collapseExpandIntroInterval = collapseExpandIntroInterval.toLong(),
        bottomToCenterTranslationInterval = bottomToCenterTranslationInterval.toLong()
    )
} 