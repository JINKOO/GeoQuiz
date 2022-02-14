package com.kjk.geoquiz.quiz

import androidx.annotation.StringRes
import java.io.Serializable

// 질문 문제 리스트 Entity
data class QuestionEntity(
    @StringRes
    val textResId: Int,
    val answer: Boolean,
    var isSolved: Boolean,
    var isCorrect: Boolean,
    var isCheated: Boolean
) : Serializable