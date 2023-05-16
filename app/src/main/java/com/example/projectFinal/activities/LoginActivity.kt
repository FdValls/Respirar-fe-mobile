package com.example.projectFinal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.projectFinal.R
import com.example.projectFinal.databinding.ActivityMainBinding
import com.example.projectFinal.databinding.ActivityNavBinding
import com.example.projectFinal.fragments.Fragment_edit_customer
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonRegister: Button
    private lateinit var userName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonLogin = binding.loginButton
        buttonRegister = binding.loginRegister
        userName = binding.username

        buttonLogin.setOnClickListener {
            val intent =
                Intent(applicationContext, NavActivity::class.java)
            startActivity(intent)
            finish()
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

        var biometricPrompt = androidx.biometric.BiometricPrompt(this, ContextCompat.getMainExecutor(this!!), object: androidx.biometric.BiometricPrompt.AuthenticationCallback(){
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