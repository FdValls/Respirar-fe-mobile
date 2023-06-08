package com.example.projectFinal.activities

import android.app.Application
import android.content.Context
import com.example.projectFinal.endPoints.RequestToken.RequestCreateTokenWithPasswd
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.preference.Preference
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.dataStore.DataStoreManager
import com.example.projectFinal.databinding.ActivityMainBinding
import com.example.projectFinal.endPoints.Request.RequestAddUserAsAnOwnerOfAnOrganization
import com.example.projectFinal.endPoints.Request.RequestAdministrationUserOrg
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.endPoints.Request.RequestRemoveUserFromOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.*
import com.example.projectFinal.endPoints.RequestToken.RequestRefreshToken
import com.example.projectFinal.endPoints.RequestToken.RequestUserInfoToken
import com.example.projectFinal.endPoints.RequestUsers.*
import com.example.projectFinal.utils.TokenClass
import com.example.projectFinal.utils.TokenClass.Companion.assignValueToGlobalVariable
import com.google.android.material.snackbar.Snackbar
//import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.prefs.Preferences
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//import sendRequest

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button
    private val requestCreateTokenWithPasswd = RequestCreateTokenWithPasswd()
    private var code : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonLogin = binding.loginButton
        buttonRegister = binding.loginRegister
        val userName = findViewById<EditText>(R.id.username).text;
        val password = findViewById<EditText>(R.id.password).text;

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
                        Toast.makeText(
                            this@LoginActivity,
                            code,
                            Toast.LENGTH_SHORT
                        ).show()
                        val myXSubjectToken = GlobalVariables.getInstance().myXSubjectToken
                        saveUserToken(myXSubjectToken);

//                      RequestUserInfoToken.sendRequest(myXSubjectToken, myXSubjectToken)
//                      RequestRefreshToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken)
//                      println("Respuesta creando users: ${RequestCreateUser.sendRequest("alice", "alice3@test.com", "test")}")
//                      println("Respuesta readUser:${RequestReadInfoUser.sendRequest("b3733a71-3764-442f-8985-36fa6124a517")}")
//                      RequestListAllUser.sendRequest()
//                      RequestUpdateUser.sendRequest("1ca75fc4-543f-4c55-bec8-fbec53e67a13")
//                      RequestDeleteUser.sendRequest("a3a948cf-cd9a-4f1d-a7ff-888e04dd16b5")
//                      RequestCreateOrganization.sendRequest("", "", "")
//                      RequestReadOrganizationDetails.sendRequest("f070b810-a8cb-4455-b30c-4b7f538046c8")
//                      RequestListAllOrganization.sendRequest()
//                      RequestUpdateOrg.sendRequest("f070b810-a8cb-4455-b30c-4b7f538046c8")
//                      RequestDeleteOrganization.sendRequest("30a92fa1-5c7b-4d72-93bf-291ba94e0da1")
//                      RequestAdministrationUserOrg.sendRequest("4d0ce57-58c8-4004-91cc-34702e7f4604","c99ba5a3-9d0b-4959-9758-9b2dec59b0fc")
//                      RequestAddUserAsAnOwnerOfAnOrganization.sendRequest("2e7d50e5-5526-412e-b577-fbfe1b14ec48","f65014e5-e7a6-4400-b938-54c8e33b83b4")
//                      RequestListUsersWithinAnOrganization.sendRequest("f65014e5-e7a6-4400-b938-54c8e33b83b4")
//                      RequestReadUserRolesWithinAnOrganization.sendRequest("4d0ce57-58c8-4004-91cc-34702e7f4604","bfc37fb9-4ccc-4fcd-b74b-87fd2b557169")
//                      RequestRemoveUserFromOrganization.sendRequest("4d0ce57-58c8-4004-91cc-34702e7f4604","bfc37fb9-4ccc-4fcd-b74b-87fd2b557169")
                        startIntent()
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
        fun getTokenFromDataStoreManager(): String {
            val sharedPref = this@LoginActivity.getPreferences(Context.MODE_PRIVATE)
            assignValueToGlobalVariable(sharedPref.getString("userToken", "")!!)
            return sharedPref.getString("userToken", "")!!
        }

        fun moveToEditCustomerFragment() {
            lifecycleScope.launch(Dispatchers.IO) {
                val userTokenFromStore = getTokenFromDataStoreManager();
                    if(userTokenFromStore != "" && userTokenFromStore != null) {
                        val request = RequestUserInfoToken.sendRequest(userTokenFromStore, userTokenFromStore)
                        if (request) {
                            startIntent()
                        }
                    } else {
                        Snackbar.make(findViewById(R.id.fragmentLogin_id),"Token deprecado",Snackbar.LENGTH_SHORT).show();
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
            biometricPrompt.authenticate(promptInfo)
        }
    }


}