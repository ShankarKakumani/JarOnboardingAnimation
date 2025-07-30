package com.shankarkakumani.domain.util

import com.shankarkakumani.common.result.Result
import com.shankarkakumani.domain.resource.exception.DataValidationException
import com.shankarkakumani.domain.resource.exception.InvalidAnimationConfigException
import com.shankarkakumani.domain.resource.model.AnimationConfigModel
import com.shankarkakumani.domain.resource.model.EducationCardModel

object OnboardingBusinessRules {
    
    const val MIN_EDUCATION_CARDS = 2
    const val MAX_EDUCATION_CARDS = 10
    const val MIN_ANIMATION_DURATION = 500L
    const val MAX_ANIMATION_DURATION = 5000L
    const val MAX_TEXT_LENGTH = 200
    
    fun validateEducationCards(cards: List<EducationCardModel>): Result<List<EducationCardModel>> {
        return when {
            cards.isEmpty() -> Result.Error(
                DataValidationException("Education cards cannot be empty")
            )
            cards.size < MIN_EDUCATION_CARDS -> Result.Error(
                DataValidationException("Minimum $MIN_EDUCATION_CARDS education cards required")
            )
            cards.size > MAX_EDUCATION_CARDS -> Result.Error(
                DataValidationException("Maximum $MAX_EDUCATION_CARDS education cards allowed")
            )
            cards.any { !it.isValid() } -> Result.Error(
                DataValidationException("One or more education cards are invalid")
            )
            else -> Result.Success(cards)
        }
    }
    
    fun validateAnimationTimings(config: AnimationConfigModel): Result<AnimationConfigModel> {
        val timings = listOf(
            config.expandCardStayInterval,
            config.collapseCardTiltInterval,
            config.collapseExpandIntroInterval,
            config.bottomToCenterTranslationInterval
        )
        
        return when {
            timings.any { it < MIN_ANIMATION_DURATION } -> Result.Error(
                InvalidAnimationConfigException("Animation duration too short (min: ${MIN_ANIMATION_DURATION}ms)")
            )
            timings.any { it > MAX_ANIMATION_DURATION } -> Result.Error(
                InvalidAnimationConfigException("Animation duration too long (max: ${MAX_ANIMATION_DURATION}ms)")
            )
            else -> Result.Success(config)
        }
    }
    
    fun validateTextContent(text: String, fieldName: String): Result<String> {
        return when {
            text.isBlank() -> Result.Error(
                DataValidationException("$fieldName cannot be blank")
            )
            text.length > MAX_TEXT_LENGTH -> Result.Error(
                DataValidationException("$fieldName exceeds maximum length of $MAX_TEXT_LENGTH characters")
            )
            else -> Result.Success(text)
        }
    }
} 