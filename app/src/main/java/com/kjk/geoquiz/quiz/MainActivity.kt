package com.kjk.geoquiz.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import com.kjk.geoquiz.R
import com.kjk.geoquiz.cheat.CheatActivity
import com.kjk.geoquiz.databinding.ActivityMainBinding
import com.kjk.geoquiz.result.ResultActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // viewBinding
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // viewModel
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(QuizViewModel::class.java)
    }

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(binding.root)
        setListener()
        initData(savedInstanceState)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when(result.resultCode) {
                RESULT_FROM_CHEAT -> {
                    Log.d(TAG, "onCreate: cheatActivityResultLauncher")
                    quizViewModel.currentQuestionIsCheated = result.data?.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false) ?: false
                    checkCheatButton()
                }
                RESULT_FROM_RESULT -> {
                    Log.d(TAG, "onCreate: resultActivityResultLauncher")
                }
            }
        }
    }

    private fun initData(savedInstanceState: Bundle?) {
        // SIS에서 데이터를 가져온다.
        // 최초 실행인 경우에는 0이다. null체크한다.
        quizViewModel.apply {
            currentIndex= savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
            cheatCount = savedInstanceState?.getInt(KEY_REMAIN_CHEAT_COUNT, 0) ?: QuizViewModel.CHEAT_MAX_COUNT
        }
        setTextSDKVersion()
        quizViewModel.setQuestionList()
        updateQuestion()
        setRemainCheatCountText()
    }

    private fun checkCheatButton() {
        // TODO 7장 챌린지 2 :: 컨닝 최대 횟수 3회이다. '컨닝하기' 버튼 비활성화.
        // '정답보기'선택 하지 않고, 돌아오는 경우가 있기 때문에 다음과 같이 처리한다.
        if (quizViewModel.currentQuestionIsCheated) {
            quizViewModel.cheatCount -= 1
            setRemainCheatCountText()
            if (!quizViewModel.isAbleToCheat()) {
                binding.cheatButton?.let {
                    it.isEnabled = false
                }
            }
        }
    }

    private fun setRemainCheatCountText() {
        binding.remainCheatCountTextView?.let {
            it.text = getString(R.string.remain_cheating_count, quizViewModel.cheatCount)
        }
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

    @SuppressLint("RestrictedApi")
    override fun onClick(view: View?) {
        binding.run {
            when (view) {
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
                    checkAllSolve()
                }
                cheatButton -> {
                    // CheatActivity로 이동한다.
//                    val intent = Intent(this@MainActivity, CheatActivity::class.java)
//                    intent.putExtra(ANSWER_IS_TRUE, quizViewModel.currentQeustionAnswer)
                    // 원래는 MainActivity -> CheatActivity로 이동할 때, 위와 같이 사용하지만,
                    // MainActivity나 App의 다른 Activity에서 CheatActivity가 어떤 IntentExtra를 받는 지 몰라도 되기 때문에, 캡슐화 한다.
                    val intent = CheatActivity.newIntent(this@MainActivity, quizViewModel.currentQuestionAnswer)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val options = ActivityOptionsCompat.makeClipRevealAnimation(
                            view!!,
                            0,
                            0,
                            view.width,
                            view.height
                        )
                        activityResultLauncher.launch(intent, options)
                    } else {
                        activityResultLauncher.launch(intent)
                    }
                }
            }
        }
    }

    private fun checkAllSolve() {
        moveToResultActivity()
//        if (quizViewModel.isAllSolved()) {
//            moveToResultActivity()
//        } else {
//            showToast(R.string.exist_non_solved_problem)
//        }
    }

    private fun moveToResultActivity() {
        val intent = ResultActivity.newIntent(this@MainActivity, quizViewModel.getQuestionList())
        activityResultLauncher.launch(intent)
    }

    private fun updateQuestion() {
        binding.questionTextView.run {
            text = makeQuestionText()
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
        if (quizViewModel.currentQuestionIsCheated) {
            showToast(R.string.judgement_toast)
        }
        val messageResId = getAnswerMessageId(userAnswer, quizViewModel.currentQuestionAnswer)
        setButtonDisable()
        showToast(messageResId)
    }

    private fun showToast(stringResId: Int) {
        Toast.makeText(this@MainActivity, stringResId, Toast.LENGTH_SHORT).show()
    }

    private fun getAnswerMessageId(userAnswer: Boolean, correctAnswer: Boolean): Int {
        return if(userAnswer == correctAnswer) {
            quizViewModel.run {
                currentQuestionIsSolved = true
                currentQuestionIsCorrect = true
            }
            R.string.answer
        } else {
            R.string.wrong_answer
        }
    }

    private fun setButtonDisable() {
        // 사용자가 답을 제출한 문제를 다시 볼때에는 true, false button을 비활성화 한다.
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

    // TODO :: 7장 챌린지 1
    private fun setTextSDKVersion() {
        binding.sdkVersionTextView.apply {
            text = getString(R.string.sdk_version_info, Build.VERSION.SDK_INT)
        }
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

    // SIS, 안드로이드 운영체제에 의해, 프로세스가 종료된면, 해당 프로세스의 모든 객체가 소멸된다.(메모리에 있는 앱의 모든 액티비티, viewModel이 제거되는데,
    // 이때, 생명주기 콜백 함수는 호출 되지 않는다. --> 그러면 Activity가 소멸될 때, UI상태 데이터를 보존하기 위한 방법은 무엇인가?
    // 안드로이드 운영체제는 SIS라는 일시적으로 Activity외부에 저장하는 데이터를 사용한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState() called")
        outState.run {
            putInt(KEY_INDEX, quizViewModel.currentIndex)
            putInt(KEY_REMAIN_CHEAT_COUNT, quizViewModel.cheatCount)
        }
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
        private const val KEY_REMAIN_CHEAT_COUNT = "remainCheatCount"
        private const val REQUEST_CODE_CHEAT = 0
        private const val ANSWER_IS_TRUE = "com.kjk.geoquiz.answer_is_true"
        private const val RESULT_FROM_CHEAT = 9001
        private const val RESULT_FROM_RESULT = 9002
    }
}