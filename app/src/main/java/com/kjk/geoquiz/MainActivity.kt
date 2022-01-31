package com.kjk.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kjk.geoquiz.data.QuestionEntity
import com.kjk.geoquiz.data.QuestionModel
import com.kjk.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener, MyInterface {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var questionLists: ArrayList<QuestionEntity> = ArrayList()
    private var currentIndex = 0
    private var model = QuestionModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setListener()
        initData()
    }

    private fun initData() {
        model.setInterface(this)
        model.createQuestionList()
        updateQuestion()
    }

    private fun setListener() {
        binding.apply {
            trueButton.setOnClickListener(this@MainActivity)
            falseButton.setOnClickListener(this@MainActivity)
            previousButton.setOnClickListener(this@MainActivity)
            nextButton.setOnClickListener(this@MainActivity)
            questionTextView.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        binding.run {
            when (v) {
                trueButton -> {
                    checkAnswer(true)
                }
                falseButton -> {
                    checkAnswer(false)
                }
                questionTextView, nextButton -> {
                   changeQuestion(1)
                }
                previousButton -> {
                    changeQuestion(-1)
                }
            }
        }
    }

    private fun changeQuestion(amount: Int) {
        if (isStartIndex()) {
            currentIndex = questionLists.size
        }
        currentIndex = (currentIndex + amount) % questionLists.size
        updateQuestion()
    }

    private fun isStartIndex(): Boolean {
        return currentIndex == 0
    }

    private fun updateQuestion() {
        binding.questionTextView.run {
            text = makeQuestionText()
            //setText(questionLists[currentIndex].textResId)
        }
    }

    private fun makeQuestionText(): String {
        return (currentIndex + 1).toString() + ". " + getString(questionLists[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionLists[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.answer
        } else {
            R.string.wrong_answer
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun getQuestionList(): ArrayList<QuestionEntity> {
        questionLists.add(QuestionEntity(R.string.question_australia, true))
        questionLists.add(QuestionEntity(R.string.question_ocean, true))
        questionLists.add(QuestionEntity(R.string.question_mideast, false))
        questionLists.add(QuestionEntity(R.string.question_africa, false))
        questionLists.add(QuestionEntity(R.string.question_americas, true))
        questionLists.add(QuestionEntity(R.string.question_asia, true))

        return questionLists
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}