package com.kjk.geoquiz.cheat

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kjk.geoquiz.quiz.QuizViewModel
import com.kjk.geoquiz.R
import com.kjk.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityCheatBinding.inflate(layoutInflater)
    }

    private val cheatViewModel by lazy {
        ViewModelProvider(this@CheatActivity).get(CheatViewModel::class.java)
    }

    // TODO 4장 챌린지 2
    private val quizViewModel by lazy {
        ViewModelProvider(this@CheatActivity).get(QuizViewModel::class.java)
    }


    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        Log.d(TAG, "onCreate()")
        checkSavedInstacneStateData(savedInstanceState)
        setListener()
        initData()
    }
    
    private fun checkSavedInstacneStateData(savedInstanceState: Bundle?) {
        cheatViewModel.isAnswerShown = savedInstanceState?.getBoolean(KEY_IS_CHEATED) ?: false
    }

    private fun setListener() {
        binding.apply {
            showAnswerButton.setOnClickListener(this@CheatActivity)
        }
    }

    private fun initData() {
        this.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        Log.d(TAG, "initData: ${answerIsTrue}")
        
        // TODO :: 4장 챌린지 1 -> (Done)
        if (cheatViewModel.isAnswerShown) {
            showAnswer()
            setAnswerShownResult(cheatViewModel.isAnswerShown)
        }
    }

    private fun showAnswer() {
        binding.answerTextView.apply {
            if (answerIsTrue) {
                setText(R.string.true_button)
            } else {
                setText(R.string.false_button)
            }
        }
        cheatViewModel.isAnswerShown = true
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        Log.d(TAG, "setAnswerShownResult: ${isAnswerShown}")
        val intent = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(RESULT_OK, intent)
    }

    override fun onClick(v: View?) {
        binding.run {
            when (v) {
                showAnswerButton -> {
                    showAnswer()
                    setAnswerShownResult(cheatViewModel.isAnswerShown)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
        outState.putBoolean(KEY_IS_CHEATED, cheatViewModel.isAnswerShown)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }


    companion object {
        private const val TAG = "CheatActivity"

        // 패키지 명으로 구분하면, 다른 앱의 Intent Extra와 충돌을 피할 수 있다.
        private const val EXTRA_ANSWER_IS_TRUE = "com.kjk.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.kjk.geoquiz.answer_shown"
        
        // 프로세스 종료에 따른 SIS데이터(컨닝여부)
        private const val KEY_IS_CHEATED = "isCheated"

        // MainActivity나 App의 다른 코드에서 CheatActivity가 Intent Extra로 무엇을 받는지 알 필요가 없다.
        // 따라서, CheatActivity가 필요로 하는 Intent를 반환하는 함수를 캡슐화해서 정의한다.
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}