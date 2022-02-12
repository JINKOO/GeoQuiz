package com.kjk.geoquiz.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kjk.geoquiz.R

class QuizViewModel : ViewModel() {

    private var questionLists: ArrayList<QuestionEntity> = ArrayList()

    var currentIndex = 0
    var isCheated = false

    var cheatCount = CHEAT_MAX_COUNT

    val currentQuestionAnswer: Boolean
        get() = questionLists[currentIndex].answer

    val currentQuestionText: Int
        get() = questionLists[currentIndex].textResId

    var currentQuestionIsSolved: Boolean = false
        get() = questionLists[currentIndex].isSolved
        set(value) {
            questionLists[currentIndex].isSolved = value
            field = value
        }

    var currentQuestionIsCheated: Boolean = false
        get() = questionLists[currentIndex].isCheated
        set(value) {
            questionLists[currentIndex].isCheated = value
            field = value
        }

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    fun setQuestionList() {
        questionLists.add(QuestionEntity(R.string.question_australia, answer = true, isSolved = false, isCheated = false))
        questionLists.add(QuestionEntity(R.string.question_ocean, answer = true, isSolved = false, isCheated = false))
        questionLists.add(QuestionEntity(R.string.question_mideast, answer = false, isSolved = false, isCheated = false))
        questionLists.add(QuestionEntity(R.string.question_africa, answer = false, isSolved = false, isCheated = false))
        questionLists.add(QuestionEntity(R.string.question_americas, answer = true, isSolved = false, isCheated = false))
        questionLists.add(QuestionEntity(R.string.question_asia, answer = true, isSolved = false, isCheated = false))
    }

    fun getQuestionList(): ArrayList<QuestionEntity> {
        return this.questionLists
    }

    fun changeQuestion(amount: Int) {
        if (isStartIndex()) {
            currentIndex = questionLists.size
        }
        currentIndex = (currentIndex + amount) % questionLists.size
    }

    private fun isStartIndex(): Boolean {
        return currentIndex == 0
    }

    fun isAbleToCheat(): Boolean {
        return cheatCount > 0
    }

    fun isAllSolved(): Boolean {
        questionLists.forEach {
            if(!it.isSolved) {
                return false
            }
        }
        return true
    }


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel instance about to be destroyed")
    }

    companion object {
        private const val TAG = "QuizViewModel"
        const val CHEAT_MAX_COUNT = 3
    }
}