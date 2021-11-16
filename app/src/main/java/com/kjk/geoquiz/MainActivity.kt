package com.kjk.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.kjk.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnTrue.setOnClickListener(this)
        binding.btnFalse.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.btnTrue -> {
                showToast(true)
            }
            binding.btnFalse -> {
                showToast(false)
            }
        }
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