package com.kjk.geoquiz.quiz

import androidx.annotation.StringRes

data class QuestionEntity(
    @StringRes
    val textResId: Int,
    val answer: Boolean,
    var isSolved: Boolean,
    var isCheated: Boolean
)