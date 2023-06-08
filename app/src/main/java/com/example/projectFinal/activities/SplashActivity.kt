package com.example.projectFinal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestToken.RequestCreateTokenWithPasswd
import com.example.projectFinal.endPoints.RequestToken.RequestRefreshToken
import com.example.projectFinal.endPoints.RequestToken.RequestUserInfoToken
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private val requestCreateTokenWithPasswd = RequestCreateTokenWithPasswd()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val myXSubjectToken = GlobalVariables.getInstance().myXSubjectToken
            RequestUserInfoToken.sendRequest(myXSubjectToken, myXSubjectToken)
            RequestRefreshToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken)
        }

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }, 0)
    }
}