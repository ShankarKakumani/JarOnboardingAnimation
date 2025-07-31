package com.shankarkakumani.domain.util

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.exception.DataValidationException
import com.shankarkakumani.domain.resource.model.AnimationConfigModel
import com.shankarkakumani.domain.resource.model.EducationCardModel

object OnboardingDomainMapper {
    
    fun mapToEducationCard(
        image: String,
        backgroundColor: String,
        expandText: String,
        collapsedText: String,
        strokeStartColor: String,
        strokeEndColor: String,
        startGradient: String = "",
        endGradient: String = ""
    ): Result<EducationCardModel> {
        
        val card = EducationCardModel(
            image = image,
            backgroundColor = backgroundColor,
            expandStateText = expandText,
            collapsedStateText = collapsedText,
            strokeStartColor = strokeStartColor,
            strokeEndColor = strokeEndColor,
            startGradient = startGradient,
            endGradient = endGradient,
            startY = 0f
        )
        
        return if (card.isValid()) {
            Result.Success(card)
        } else {
            Result.Error(DataValidationException("Invalid education card data"))
        }
    }
    
    fun mapToAnimationConfig(
        expandStay: Int,
        collapseTilt: Int,
        introCollapse: Int,
        translation: Int
    ): Result<AnimationConfigModel> {
        
        val config = AnimationConfigModel(
            expandCardStayInterval = expandStay.toLong(),
            collapseCardTiltInterval = collapseTilt.toLong(),
            collapseExpandIntroInterval = introCollapse.toLong(),
            bottomToCenterTranslationInterval = translation.toLong()
        )
        
        return OnboardingBusinessRules.validateAnimationTimings(config)
    }
} 