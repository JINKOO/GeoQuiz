package com.kjk.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kjk.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener/*, MyInterface*/ {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    // viewModel선언
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(binding.root)
        setListener()
        initData()
    }

    private fun initData() {
        quizViewModel.setQuestionList()
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
                    quizViewModel.changeQuestion(1)
                    updateQuestion()
                }
                previousButton -> {
                    quizViewModel.changeQuestion(-1)
                    updateQuestion()
                }
                submitButton -> {
                    //TODO 3장 챌린지 2: 점수 보여 주기
                }
            }
        }
    }

    private fun updateQuestion() {
        binding.questionTextView.run {
            text = makeQuestionText()
            //setText(questionLists[currentIndex].textResId)
            if (quizViewModel.currentQuestionIsSolved) {
                setButtonDisable()
            } else {
                setButtonEnable()
            }
        }
    }

    private fun makeQuestionText(): String {
        return (quizViewModel.currentIndex + 1).toString() + ". " + getString(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.run {
                getQuestionList()[currentIndex].isSolved = true
            }
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

//    override fun getQuestionList(): ArrayList<QuestionEntity> {
//        questionLists.add(QuestionEntity(R.string.question_australia, answer = true, isSolved = false))
//        questionLists.add(QuestionEntity(R.string.question_ocean, answer = true, isSolved = false))
//        questionLists.add(QuestionEntity(R.string.question_mideast, answer = false, isSolved = false))
//        questionLists.add(QuestionEntity(R.string.question_africa, answer = false, isSolved = false))
//        questionLists.add(QuestionEntity(R.string.question_americas, answer = true, isSolved = false))
//        questionLists.add(QuestionEntity(R.string.question_asia, answer = true, isSolved = false))
//
//        return questionLists
//    }

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