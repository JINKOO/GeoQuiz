package com.kjk.geoquiz.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kjk.geoquiz.R
import com.kjk.geoquiz.databinding.ActivityResultBinding
import com.kjk.geoquiz.quiz.QuestionEntity

class ResultActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }

    private val problemList: ArrayList<QuestionEntity> = ArrayList<QuestionEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {

        }
    }

    private fun moveToMainActivity() {
        setResult(9002)
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
}