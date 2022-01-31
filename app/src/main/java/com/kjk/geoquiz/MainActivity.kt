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
        Log.d(TAG, "onCreate: ")
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
            submitButton.setOnClickListener(this@MainActivity)
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
                submitButton -> {
                    //TODO 3장 챌린지 2: 점수 보여 주기
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
            if (questionLists[currentIndex].isSolved) {
                setButtonDisable()
            } else {
                setButtonEnable()
            }
        }
    }

    private fun makeQuestionText(): String {
        return (currentIndex + 1).toString() + ". " + getString(questionLists[currentIndex].textResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionLists[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            questionLists[currentIndex].isSolved = true
            setButtonDisable()
            R.string.answer
        } else {
            R.string.wrong_answer
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun setButtonDisable() {
        // 사용자가 정답을 맞춘 문제를 다시 볼때에는 true, false button을 비활성화 한다.
        binding.run {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
    }

    private fun setButtonEnable() {
        binding.run {
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    override fun getQuestionList(): ArrayList<QuestionEntity> {
        questionLists.add(QuestionEntity(R.string.question_australia, answer = true, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_ocean, answer = true, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_mideast, answer = false, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_africa, answer = false, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_americas, answer = true, isSolved = false))
        questionLists.add(QuestionEntity(R.string.question_asia, answer = true, isSolved = false))

        return questionLists
    }

    //  Activity 생명 주기 Log 찍기.
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}