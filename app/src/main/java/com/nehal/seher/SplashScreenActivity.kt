package com.nehal.seher

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nehal.seher.utils.AppPreferences

class SplashScreenActivity : AppCompatActivity() {
    private val splashTime = 1000L // 1 seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            decisionToGo()
        }, splashTime)
    }

    private fun decisionToGo() {
        MainActivity.open(this, AppPreferences.isFirstTimeInstall)
        finish()
    }
}