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

//    private val questionLists = listOf(
//            QuestionEntity(R.string.question_australia, true),
//            QuestionEntity(R.string.question_ocean, true),
//            QuestionEntity(R.string.question_mideast, false),
//            QuestionEntity(R.string.question_africa, false),
//            QuestionEntity(R.string.question_americas, true),
//            QuestionEntity(R.string.question_asia, true)
//    )

    private var questionLists: ArrayList<QuestionEntity> = ArrayList()

    private var currentIndex = 0;

    private var model = QuestionModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /** 질문 리스트 초기화하기 위한 */
        model.setInterface(this)

        model.createQuestionList()

        binding.trueButton.setOnClickListener(this)
        binding.falseButton.setOnClickListener(this)
        binding.nextButton.setOnClickListener(this)

        updateQuestion()
    }


    override fun onClick(v: View?) {
        when (v) {
            binding.trueButton -> {
                //showToast(true)
                checkAnswer(true)
            }
            binding.falseButton -> {
                //showToast(false)
                checkAnswer(false)
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

    /**
     * @param userAnswer : 사용자가 True, false 중 어떤 버튼을 클릭했는지 식별하는 변수
     */
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionLists[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.answer
        } else {
            R.string.wrong_answer
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }


    override fun getQuestionString(): ArrayList<QuestionEntity> {
//        Log.w("1111", "getQuestionString() :: ")
//        return getString(R.string.question_asia)
        questionLists.add(QuestionEntity(R.string.question_australia, true))
        questionLists.add(QuestionEntity(R.string.question_ocean, true))
        questionLists.add(QuestionEntity(R.string.question_mideast, false))
        questionLists.add(QuestionEntity(R.string.question_africa, false))
        questionLists.add(QuestionEntity(R.string.question_americas, true))
        questionLists.add(QuestionEntity(R.string.question_asia, true))

        return questionLists
    }
}