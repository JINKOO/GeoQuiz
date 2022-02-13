package com.kjk.geoquiz.result

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kjk.geoquiz.R
import com.kjk.geoquiz.databinding.ActivityResultBinding
import com.kjk.geoquiz.quiz.QuestionEntity

class ResultActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    private val resultViewModel by lazy {
        ViewModelProvider(this@ResultActivity).get(ResultViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        setListener()
        initData()
    }

    private fun setListener() {
        binding.apply {
            confirmButton.setOnClickListener(this@ResultActivity)
        }
    }

    private fun initData() {
        resultViewModel.questionList = intent.getSerializableExtra(EXTRA_QUESTION_LIST) as ArrayList<QuestionEntity>
        Log.d(TAG, "initData: ${resultViewModel.questionList}")

        binding.apply {
            correctAnswersTextView.text = getString(R.string.correct_answers, resultViewModel.getCorrectAnswer(), resultViewModel.questionList.size)
            answerRateTextView.text = getString(R.string.answer_rate, resultViewModel.getAnswerRate())
        }
    }


    private fun moveToMainActivity() {
        setResult(RESULT_OK)
        finish()
    }

    override fun onClick(view: View?) {
        binding.apply {
            when(view) {
                confirmButton -> {
                    moveToMainActivity()
                }
            }
        }
    }

    companion object {
        private const val TAG = "ResultActivity"
        const val EXTRA_QUESTION_LIST = "com.kjk.geoquiz.question_list"

        fun newIntent(packageContext: Context, questionList: ArrayList<QuestionEntity>): Intent {
            return Intent(packageContext, ResultActivity::class.java).apply {
                putExtra(EXTRA_QUESTION_LIST, questionList)
            }
        }
    }
}