package com.kjk.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel
import com.kjk.geoquiz.data.QuestionEntity

class QuizViewModel : ViewModel() {

    private var questionLists: ArrayList<QuestionEntity> = ArrayList()

    var currentIndex = 0

    val currentQuestionAnswer: Boolean
        get() = questionLists[currentIndex].answer

    val currentQuestionText: Int
        get() = questionLists[currentIndex].textResId

    val currentQuestionIsSolved: Boolean
        get() = questionLists[currentIndex].isSolved

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    fun setQuestionList() {
        questionLists.add(QuestionEntity(R.string.question_australia, answer = true, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_ocean, answer = true, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_mideast, answer = false, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_africa, answer = false, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_americas, answer = true, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_asia, answer = true, isSolved = false))
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

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel instance about to be destroyed")
    }

    companion object {
        private const val TAG = "QuizViewModel"
    }
}