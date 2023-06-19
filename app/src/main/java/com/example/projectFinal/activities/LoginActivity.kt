package com.example.projectFinal.activities

import android.content.Context
import com.example.projectFinal.endPoints.RequestToken.RequestCreateTokenWithPasswd
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.databinding.ActivityMainBinding
import com.example.projectFinal.endPoints.RequestOrganizations.*
import com.example.projectFinal.endPoints.RequestToken.RequestRefreshToken
import com.example.projectFinal.endPoints.RequestToken.RequestUserInfoToken
import com.example.projectFinal.endPoints.RequestUsers.*
import com.example.projectFinal.utils.TokenClass.Companion.assignValueToGlobalVariable
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonLogin: Button
    private val requestCreateTokenWithPasswd = RequestCreateTokenWithPasswd()
    private var code : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonLogin = binding.loginButton
        val userName = findViewById<EditText>(R.id.username).text;
        val password = findViewById<EditText>(R.id.password).text;
        val fingerPrint = findViewById<TextView>(R.id.loginBiometricButton);

        fun hasNotEmptyFields(): Boolean {
            var canPass = false;
            if(userName.isNotEmpty() && password.isNotEmpty()){
                canPass = true;
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Los campos deben estar llenos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return canPass
        }

        fun saveUserToken(token: String ){
            val sharedPref = this@LoginActivity.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString("userToken", token)
                apply()
            }
            assignValueToGlobalVariable(token)

        }

        fun startIntent() {
            val intent =
                Intent(applicationContext, NavActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonLogin.setOnClickListener {
            if(hasNotEmptyFields()) {
                lifecycleScope.launch {
                    code = requestCreateTokenWithPasswd.sendRequest(
                        userName.toString(),
                        password.toString()
                    )
                    if (code == "201"){
                        val myXSubjectToken = GlobalVariables.getInstance().myXSubjectToken

                        RequestUserInfoToken.sendRequest(myXSubjectToken, myXSubjectToken)
                        RequestRefreshToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken)
                        saveUserToken(myXSubjectToken)

                        RequestListAllOrganization.sendRequest()
                        RequestListAllUser.sendRequest()
                        println("myXSubjectTokenmyXSubjectTokenmyXSubjectToken" + myXSubjectToken)

                        RequestUserInfoToken.sendRequest(myXSubjectToken, myXSubjectToken)
                        startIntent()
                    } else {
                        Snackbar.make(findViewById(R.id.fragmentLogin_id),"Username o Password incorrectos! ",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        }

        fun getTokenFromDataStoreManager(): String {
            val sharedPref = this@LoginActivity.getPreferences(Context.MODE_PRIVATE)
            assignValueToGlobalVariable(sharedPref.getString("userToken", "")!!)
            return sharedPref.getString("userToken", "")!!
        }

        fun moveToEditCustomerFragment() {
            lifecycleScope.launch(Dispatchers.IO) {
                val userTokenFromStore = getTokenFromDataStoreManager();
                val request = RequestUserInfoToken.sendRequest(userTokenFromStore, userTokenFromStore)
                if (request) {
                    RequestListAllOrganization.sendRequest()
                    startIntent()
                } else if(RequestUserInfoToken.returnCode() == "401" ) {
                    Snackbar.make(findViewById(R.id.fragmentLogin_id),"Sesion expirada. Ingrese con Username y Password!",Snackbar.LENGTH_SHORT).show();
                }
            }
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
            val userTokenFromStore = getTokenFromDataStoreManager();
            if(userTokenFromStore != "" && userTokenFromStore != null)
                biometricPrompt.authenticate(promptInfo)
            else
                Snackbar.make(findViewById(R.id.fragmentLogin_id),"Primer login ingresar con Username y Password!",Snackbar.LENGTH_SHORT).show();
        }
    }


}