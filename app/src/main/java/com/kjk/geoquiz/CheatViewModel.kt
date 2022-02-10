package com.kjk.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

// 장치 회전, 프로세스 종료 시 CheatActivity의 UI상태 보존.
class CheatViewModel : ViewModel() {

    var isAnswerShown = false

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }

    companion object {
        private const val TAG = "CheatViewModel"
    }
}