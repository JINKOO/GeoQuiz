package com.kjk.geoquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kjk.geoquiz.databinding.ActivityCheatBinding

class CheatActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityCheatBinding.inflate(layoutInflater)
    }

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d(TAG, "onCreate()")
        setListener()
        initData()
    }

    private fun setListener() {
        binding.apply {
            showAnswerButton.setOnClickListener(this@CheatActivity)
        }
    }

    private fun initData() {
        this.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        Log.d(TAG, "initData: ${answerIsTrue}")
    }

    private fun showAnswer() {
        binding.answerTextView.apply {
            if (answerIsTrue) {
                setText(R.string.true_button)
            } else {
                setText(R.string.false_button)
            }
        }
    }

    override fun onClick(v: View?) {
        binding.run {
            when (v) {
                showAnswerButton -> {
                    showAnswer()
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }


    companion object {
        private const val TAG = "CheatActivity"

        // 패키지 명으로 구분하면, 다른 앱의 Intent Extra와 충돌을 피할 수 있다.
        private const val EXTRA_ANSWER_IS_TRUE = "com.kjk.geoquiz.answer_is_true"

        // MainActivity나 App의 다른 코드에서 CheatActivity가 Intent Extra로 무엇을 받는지 알 필요가 없다.
        // 따라서, CheatActivity가 필요로 하는 Intent를 반환하는 함수를 캡슐화해서 정의한다.
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}