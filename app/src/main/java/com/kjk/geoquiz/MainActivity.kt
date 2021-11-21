package com.kjk.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.kjk.geoquiz.data.QuestionEntity
import com.kjk.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val questionLists = listOf(
            QuestionEntity(R.string.question_australia, true),
            QuestionEntity(R.string.question_ocean, true),
            QuestionEntity(R.string.question_mideast, false),
            QuestionEntity(R.string.question_africa, false),
            QuestionEntity(R.string.question_americas, true),
            QuestionEntity(R.string.question_asia, true)
    )

    private var currentIndex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener(this)
        binding.falseButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)

        updateQuestion()
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.trueButton -> {
                showToast(true)
            }
            binding.falseButton -> {
                showToast(false)
            }
            binding.nextButton -> {
                //TODO 다음 질문
                currentIndex = (currentIndex + 1) % questionLists.size
                updateQuestion()
            }
        }
    }

    private fun updateQuestion() {
        binding.questionTextView.setText(questionLists[currentIndex].textResId)
    }

    private fun showToast(isAnswer: Boolean) {
        if (isAnswer) {
            var toast = Toast.makeText(this, getString(R.string.answer), Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 100)
            toast.show()

        } else {
            Toast.makeText(this, getString(R.string.wrong_answer), Toast.LENGTH_SHORT).show()
        }
    }
}