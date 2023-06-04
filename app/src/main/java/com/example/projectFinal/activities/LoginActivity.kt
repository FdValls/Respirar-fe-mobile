package com.example.projectFinal.activities

import com.example.projectFinal.endPoints.RequestToken.RequestCreateTokenWithPasswd
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.databinding.ActivityMainBinding
import com.example.projectFinal.endPoints.Request.RequestAddUserAsAnOwnerOfAnOrganization
import com.example.projectFinal.endPoints.Request.RequestAdministrationUserOrg
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.endPoints.Request.RequestRemoveUserFromOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.*
import com.example.projectFinal.endPoints.RequestToken.RequestRefreshToken
import com.example.projectFinal.endPoints.RequestToken.RequestUserInfoToken
import com.example.projectFinal.endPoints.RequestUsers.*
import kotlinx.coroutines.launch

//import sendRequest

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonRegister: Button
    private val requestCreateTokenWithPasswd = RequestCreateTokenWithPasswd()
    private var code : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonLogin = binding.loginButton
        buttonRegister = binding.loginRegister
//        val userName = findViewById<EditText>(R.id.username).text;
        val userName = "admin@test.com";
        val password = "1234";
//        val password = findViewById<EditText>(R.id.password).text;
        var userToken = "";

        fun hasNotEmptyFields(): Boolean {
            var canPass = false;
            if(userName.isNotEmpty() && password.isNotEmpty()){
                canPass = true;
                Toast.makeText(
                    this@LoginActivity,
                    "Los campos deben estar llenos" + userName + password,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Los campos deben estar llenos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return canPass
        }

        buttonLogin.setOnClickListener {
            if(hasNotEmptyFields()) {
                lifecycleScope.launch {
                    code = requestCreateTokenWithPasswd.sendRequest(
                        userName,
                        password
                    )
                    if (code == "201"){
                        RequestListAllOrganization.sendRequest()
                        RequestListAllUser.sendRequest()
                        Toast.makeText(
                            this@LoginActivity,
                            code,
                            Toast.LENGTH_SHORT
                        ).show()
                        val myXSubjectToken = GlobalVariables.getInstance().myXSubjectToken
                        userToken = myXSubjectToken;
                        Toast.makeText(
                            this@LoginActivity,
                            "The user token is: " + userToken,
                            Toast.LENGTH_SHORT
                        ).show()
//                       RequestUserInfoToken.sendRequest(myXSubjectToken, myXSubjectToken)
//                        RequestRefreshToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken)
//                        println("Respuesta creando users: ${RequestCreateUser.sendRequest("alice", "alice3@test.com", "test")}")
//                        println("Respuesta readUser:${RequestReadInfoUser.sendRequest("b3733a71-3764-442f-8985-36fa6124a517")}")
//                        RequestListAllUser.sendRequest()
                        RequestUpdateUser.sendRequest("1ca75fc4-543f-4c55-bec8-fbec53e67a13")
//                        RequestDeleteUser.sendRequest("a3a948cf-cd9a-4f1d-a7ff-888e04dd16b5")
//                        RequestCreateOrganization.sendRequest("", "", "")
//                        RequestReadOrganizationDetails.sendRequest("f070b810-a8cb-4455-b30c-4b7f538046c8")
//                        RequestListAllOrganization.sendRequest()
//                        RequestUpdate91cc-34702e7f4604","bfc37fb9-4ccc-4fcd-b74b-87fd2b557169")
                        val intent =
                            Intent(applicationContext, NavActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            code,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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

        fun moveToEditCustomerFragment(): Boolean {
            if(userToken.isNotEmpty()) {
                val intent =
                    Intent(applicationContext, NavActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(
                    this,
                    "Login succeeded with finger print",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Please login with username for the first time",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
            return true

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