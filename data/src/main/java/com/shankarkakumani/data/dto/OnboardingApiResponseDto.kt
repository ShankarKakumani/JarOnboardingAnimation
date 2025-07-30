package com.shankarkakumani.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnboardingApiResponseDto(
    @SerialName("data") val data: OnboardingDataWrapperDto,
    @SerialName("success") val success: Boolean
)

@Serializable
data class OnboardingDataWrapperDto(
    @SerialName("manualBuyEducationData") val onboardingScreen: OnboardingScreenDataDto
)

@Serializable
data class OnboardingScreenDataDto(
    @SerialName("introTitle") val introTitle: String,
    @SerialName("introSubtitle") val introSubtitle: String,
    @SerialName("ctaLottie") val ctaLottie: String,
    @SerialName("educationCardList") val educationCards: List<EducationCardDataDto>,
    @SerialName("saveButtonCta") val saveButton: SaveButtonDataDto,
    @SerialName("toolBarIcon") val toolBarIcon: String,
    @SerialName("toolBarText") val toolBarText: String,
    @SerialName("expandCardStayInterval") val expandCardStayInterval: Int,
    @SerialName("collapseCardTiltInterval") val collapseCardTiltInterval: Int,
    @SerialName("collapseExpandIntroInterval") val collapseExpandIntroInterval: Int,
    @SerialName("bottomToCenterTranslationInterval") val bottomToCenterTranslationInterval: Int
)

@Serializable
data class EducationCardDataDto(
    @SerialName("image") val image: String,
    @SerialName("endGradient") val endGradient: String,
    @SerialName("startGradient") val startGradient: String,
    @SerialName("strokeEndColor") val strokeEndColor: String,
    @SerialName("backGroundColor") val backgroundColor: String,
    @SerialName("expandStateText") val expandStateText: String,
    @SerialName("strokeStartColor") val strokeStartColor: String,
    @SerialName("collapsedStateText") val collapsedStateText: String
)

@Serializable
data class SaveButtonDataDto(
    @SerialName("text") val text: String,
    @SerialName("textColor") val textColor: String,
    @SerialName("strokeColor") val strokeColor: String,
    @SerialName("backgroundColor") val backgroundColor: String,
    @SerialName("icon") val icon: String?,
    @SerialName("deeplink") val deeplink: String?
) 