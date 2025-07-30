package com.shankarkakumani.data.mapper

import com.shankarkakumani.data.client.room.entity.*
import com.shankarkakumani.domain.resource.model.*

// Entity to Domain Model mappings
fun OnboardingWithDetailsEntity.toOnboardingDataModel(): OnboardingDataModel {
    return OnboardingDataModel(
        introTitle = onboarding.introTitle,
        introSubtitle = onboarding.introSubtitle,
        ctaLottie = onboarding.ctaLottie,
        educationCards = educationCards.sortedBy { it.displayOrder }.map { it.toEducationCardModel() },
        saveButton = saveButton.toSaveButtonModel(),
        toolBarIcon = onboarding.toolBarIcon,
        toolBarText = onboarding.toolBarText,
        animationConfig = onboarding.toAnimationConfigModel()
    )
}

fun EducationCardEntity.toEducationCardModel(): EducationCardModel {
    return EducationCardModel(
        id = id,
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

fun SaveButtonCtaEntity.toSaveButtonModel(): SaveButtonModel {
    return SaveButtonModel(
        text = text,
        textColor = textColor,
        strokeColor = strokeColor,
        backgroundColor = backgroundColor,
        icon = icon,
        deeplink = deeplink
    )
}

fun OnboardingEntity.toAnimationConfigModel(): AnimationConfigModel {
    return AnimationConfigModel(
        expandCardStayInterval = expandCardStayInterval.toLong(),
        collapseCardTiltInterval = collapseCardTiltInterval.toLong(),
        collapseExpandIntroInterval = collapseExpandIntroInterval.toLong(),
        bottomToCenterTranslationInterval = bottomToCenterTranslationInterval.toLong()
    )
} 