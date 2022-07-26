package br.com.zup.marvel.presentation.splash.activityview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.zup.marvel.R
import br.com.zup.marvel.presentation.home.view.HomeActivity
import br.com.zup.marvel.presentation.login.activityview.LoginActivity
import java.util.*

class SplashActivity : AppCompatActivity() {
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        timer.schedule(object : TimerTask() {
            override fun run() {
                jump()
            }
        }, 3000)
    }

    private fun jump() {
        timer.cancel()
        startActivity(Intent(this, LoginActivity::class.java))
        this.finish()
    }
}