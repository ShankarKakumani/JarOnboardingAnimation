package com.shankarkakumani.domain.resource.model

data class OnboardingDataModel(
    val introTitle: String,
    val introSubtitle: String,
    val ctaLottie: String,
    val educationCards: List<EducationCardModel>,
    val saveButton: SaveButtonModel,
    val toolBarIcon: String,
    val toolBarText: String,
    val animationConfig: AnimationConfigModel
) {
    fun isValid(): Boolean {
        return introTitle.isNotBlank() &&
               introSubtitle.isNotBlank() &&
               educationCards.isNotEmpty() &&
               animationConfig.isValid()
    }
    
    fun hasMinimumCards(): Boolean = educationCards.size >= 2
    
    fun getDisplayableCards(): List<EducationCardModel> {
        return educationCards.filter { it.isValid() }
    }
} 