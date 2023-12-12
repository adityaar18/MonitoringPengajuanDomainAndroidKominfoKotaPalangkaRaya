package com.aditya.pengajuanapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.aditya.pengajuanapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        binding.apply {
            topSplash.animation = topAnim
            bottomSplash.animation = bottomAnim
        }

        goToMain()
    }

    private fun goToMain(){
        lifecycleScope.launch(Dispatchers.Main) {
            delay(5000)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }
}