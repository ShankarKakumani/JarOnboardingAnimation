package com.shankarkakumani.data.mapper

import com.shankarkakumani.data.client.room.entity.*
import com.shankarkakumani.data.dto.*
import java.util.UUID

// DTO to Entity mappings
fun OnboardingApiResponseDto.toOnboardingEntity(): OnboardingEntity {
    return OnboardingEntity(
        introTitle = data.onboardingScreen.introTitle,
        introSubtitle = data.onboardingScreen.introSubtitle,
        ctaLottie = data.onboardingScreen.ctaLottie,
        toolBarIcon = data.onboardingScreen.toolBarIcon,
        toolBarText = data.onboardingScreen.toolBarText,
        expandCardStayInterval = data.onboardingScreen.expandCardStayInterval,
        collapseCardTiltInterval = data.onboardingScreen.collapseCardTiltInterval,
        collapseExpandIntroInterval = data.onboardingScreen.collapseExpandIntroInterval,
        bottomToCenterTranslationInterval = data.onboardingScreen.bottomToCenterTranslationInterval,
        lastUpdated = System.currentTimeMillis()
    )
}

fun List<EducationCardDataDto>.toEducationCardEntities(onboardingId: String = "onboarding_data"): List<EducationCardEntity> {
    return mapIndexed { index, dto ->
        EducationCardEntity(
            id = UUID.randomUUID().toString(),
            onboardingId = onboardingId,
            image = dto.image,
            endGradient = dto.endGradient,
            startGradient = dto.startGradient,
            strokeEndColor = dto.strokeEndColor,
            backgroundColor = dto.backgroundColor,
            expandStateText = dto.expandStateText,
            strokeStartColor = dto.strokeStartColor,
            collapsedStateText = dto.collapsedStateText,
            displayOrder = index
        )
    }
}

fun SaveButtonDataDto.toSaveButtonCtaEntity(onboardingId: String = "onboarding_data"): SaveButtonCtaEntity {
    return SaveButtonCtaEntity(
        onboardingId = onboardingId,
        text = text,
        textColor = textColor,
        strokeColor = strokeColor,
        backgroundColor = backgroundColor,
        icon = icon,
        deeplink = deeplink
    )
} 