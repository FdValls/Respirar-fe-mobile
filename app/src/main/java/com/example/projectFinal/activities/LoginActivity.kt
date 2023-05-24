package com.example.projectFinal.activities

import RequestManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.R
import com.example.projectFinal.databinding.ActivityMainBinding
import com.example.projectFinal.entities.GlobalVariables
import com.example.projectFinal.entities.RequestRefreshToken
import com.example.projectFinal.entities.RequestUserInfoToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//import sendRequest

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonRegister: Button
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private val requestManager = RequestManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonLogin = binding.loginButton
        buttonRegister = binding.loginRegister
        userName = binding.username
        password = binding.password

        buttonLogin.setOnClickListener {
            lifecycleScope.launch {
                if (requestManager.sendRequest(
                        "admin@test.com",
                        "1234"
                    )
                ) {
                    Toast.makeText(this@LoginActivity, requestManager.retunCode(), Toast.LENGTH_SHORT).show()
                    val myXSubjectToken = GlobalVariables.getInstance().myXSubjectToken
                    RequestUserInfoToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken, GlobalVariables.getInstance().myXSubjectToken)
                    RequestRefreshToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken,)
                    println("Valor de myXSubjectToken: $myXSubjectToken")

                } else {
                    Toast.makeText(this@LoginActivity, requestManager.retunCode(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        buttonRegister.setOnClickListener {
            Toast.makeText(
                this,
                "Probando boton REGISTER!",
                Toast.LENGTH_SHORT
            ).show()
        }

        val fingerPrint = findViewById<TextView>(R.id.loginBiometricButton);
        //val context = activity?.applicationContext

        var executor = ContextCompat.getMainExecutor(this!!)
        var canAuthenticate = false

        fun moveToEditCustomerFragment() {
            val intent =
                Intent(applicationContext, NavActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(
                this,
                "Login succeeded with finger print",
                Toast.LENGTH_SHORT
            ).show()
        }

        var biometricPrompt = androidx.biometric.BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this!!),
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    moveToEditCustomerFragment()
                }
            })

        var promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using your finger print")
            .setNegativeButtonText("Cancel")
            .build()

        fingerPrint.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }


}