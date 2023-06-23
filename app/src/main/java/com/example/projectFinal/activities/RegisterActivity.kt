package com.example.projectFinal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestCreateUser
import kotlinx.coroutines.launch
import values.objStrings
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerBtn: Button
    private var checkEmail by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.nameUserText)
        emailEditText = findViewById(R.id.emailEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        registerBtn = findViewById(R.id.id_registerBtn)

        registerBtn.setOnClickListener {
            val username = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            fun showMessage(message: String) {
                Toast.makeText(
                    this@RegisterActivity,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            if (password == confirmPassword) {
                if (!username.isEmpty() && !email.isEmpty()) {
                    lifecycleScope.launch {
                        RequestCreateUser.sendRequest(username, email, confirmPassword)
                        if (RequestCreateUser.returnCodeCreateUser() == "201") {
                            showMessage(objStrings.created_successfully)
                            finish()
                        } else if (RequestCreateUser.returnCodeCreateUser() == "409") {
                            showMessage(objStrings.email_in_use);
                        }
                    }
                }else{
                    showMessage(objStrings.fields_required)
                }
            }else{
                showMessage(objStrings.pass_not_equal)
            }
        }
    }
}