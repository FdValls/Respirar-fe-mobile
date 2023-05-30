package com.example.projectFinal.activities

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
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private val requestCreateTokenWithPasswd = RequestCreateTokenWithPasswd()
    private var code : String = ""

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
                code = requestCreateTokenWithPasswd.sendRequest(
                    "admin@test.com",
                    "1234"
                )
                if (code == "201"){
                    Toast.makeText(
                        this@LoginActivity,
                        code,
                        Toast.LENGTH_SHORT
                    ).show()
                    val myXSubjectToken = GlobalVariables.getInstance().myXSubjectToken


                    RequestUserInfoToken.sendRequest(myXSubjectToken, myXSubjectToken)
                    RequestRefreshToken.sendRequest(GlobalVariables.getInstance().myXSubjectToken)
                    RequestCreateUser.sendRequest("alice", "alice3@test.com", "test")
                    RequestReadInfoUser.sendRequest("1ca75fc4-543f-4c55-bec8-fbec53e67a13")
                    RequestListAllUser.sendRequest()
                    RequestUpdateUser.sendRequest("1ca75fc4-543f-4c55-bec8-fbec53e67a13")
                    RequestDeleteUser.sendRequest("a3a948cf-cd9a-4f1d-a7ff-888e04dd16b5")
                    RequestCreateOrganization.sendRequest("", "", "")
                    RequestReadOrganizationDetails.sendRequest("f070b810-a8cb-4455-b30c-4b7f538046c8")
                    RequestListAllOrganization.sendRequest()
                    RequestUpdateOrg.sendRequest("f070b810-a8cb-4455-b30c-4b7f538046c8")
                    RequestDeleteOrganization.sendRequest("30a92fa1-5c7b-4d72-93bf-291ba94e0da1")
                    RequestAdministrationUserOrg.sendRequest("1afe3995-00cd-4df1-9d77-67d000c75e49","bfc37fb9-4ccc-4fcd-b74b-87fd2b557169")
                    RequestAddUserAsAnOwnerOfAnOrganization.sendRequest("2e7d50e5-5526-412e-b577-fbfe1b14ec48","f65014e5-e7a6-4400-b938-54c8e33b83b4")
                    RequestListUsersWithinAnOrganization.sendRequest("f65014e5-e7a6-4400-b938-54c8e33b83b4")
                    RequestReadUserRolesWithinAnOrganization.sendRequest("1afe3995-00cd-4df1-9d77-67d000c75e49","bfc37fb9-4ccc-4fcd-b74b-87fd2b557169")
                    RequestRemoveUserFromOrganization.sendRequest("1afe3995-00cd-4df1-9d77-67d000c75e49","bfc37fb9-4ccc-4fcd-b74b-87fd2b557169")

                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        code,
                        Toast.LENGTH_SHORT
                    ).show()
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