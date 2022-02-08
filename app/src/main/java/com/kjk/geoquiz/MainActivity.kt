package com.kjk.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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

        // SIS에서 데이터를 가져온다.
        // 최초 실행인 경우에는 0이다. null체크한다.
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

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
            cheatButton?.setOnClickListener(this@MainActivity)
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
                cheatButton -> {
                    // CheatActivity로 이동한다.
                    val intent = Intent(this@MainActivity, CheatActivity::class.java)
                    startActivity(intent)
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

    // SIS, 안드로이드 운영체제에 의해, 프로세스가 종료된면, 해당 프로세스의 모든 객체가 소멸된다.(메모리에 있는 앱의 모든 액티비티, viewModel이 제거되는데,
    // 이때, 생명주기 콜백 함수는 호출 되지 않는다. --> 그러면 Activity가 소멸될 때, UI상태 데이터를 보존하기 위한 방법은 무엇인가?
    // 안드로이드 운영체제는 SIS라는 일시적으로 Activity외부에 저장하는 데이터를 사용한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState() called")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
        private const val KEY_INDEX = "index"
    }
}