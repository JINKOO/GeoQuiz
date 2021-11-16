package com.kjk.geoquiz.model

import androidx.annotation.StringRes

data class QuestionEntity(
    @StringRes
    val textResId: Int,
    val answer: Boolean
)