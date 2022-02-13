package com.kjk.geoquiz.result

import androidx.lifecycle.ViewModel
import com.kjk.geoquiz.quiz.QuestionEntity

class ResultViewModel : ViewModel() {

    var questionList: ArrayList<QuestionEntity> = ArrayList()

    fun getCorrectAnswer(): Int {
        var count = 0
        questionList.forEach {
            if (it.isCorrect) {
                count++
            }
        }
        return count
    }


    fun getAnswerRate(): Double {
        return (getCorrectAnswer().toDouble() / questionList.size.toDouble()) * MAX_PERCENTAGE
    }

    companion object {
        private const val TAG = "ResultViewModel"
        private const val MAX_PERCENTAGE = 100.0
    }

}