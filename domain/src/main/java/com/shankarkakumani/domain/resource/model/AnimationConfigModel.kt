package com.shankarkakumani.domain.resource.model

data class AnimationConfigModel(
    val expandCardStayInterval: Long,
    val collapseCardTiltInterval: Long,
    val collapseExpandIntroInterval: Long,
    val bottomToCenterTranslationInterval: Long
) {
    fun isValid(): Boolean {
        return expandCardStayInterval > 0 &&
               collapseCardTiltInterval > 0 &&
               collapseExpandIntroInterval > 0 &&
               bottomToCenterTranslationInterval > 0
    }
    
    fun getTotalAnimationDuration(): Long {
        return expandCardStayInterval + 
               collapseCardTiltInterval + 
               collapseExpandIntroInterval + 
               bottomToCenterTranslationInterval
    }
    
    fun isReasonableSpeed(): Boolean {
        val totalDuration = getTotalAnimationDuration()
        return totalDuration in 1000..10000 // Between 1-10 seconds per card
    }
} 