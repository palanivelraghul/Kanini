package com.task.kaninieventvenu.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.task.kaninieventvenu.baseUtils.BaseActivity
import com.task.kaninieventvenu.databinding.ActivitySplashScreenBinding

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashScreenBinding.inflate(layoutInflater).root)
        moveNextScreen()
    }

    private fun moveNextScreen() {
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, EventsListActivity::class.java))
            finish()
        }, 2000)
    }
}