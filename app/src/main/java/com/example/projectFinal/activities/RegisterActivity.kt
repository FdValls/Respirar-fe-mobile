package com.example.projectFinal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestCreateUser
import kotlinx.coroutines.launch
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

            Log.d("Registro", "Username: $username")
            Log.d("Registro", "Email: $email")
            Log.d("Registro", "Contraseña: $password")
            Log.d("Registro", "Confirmar contraseña: $confirmPassword")

            if (password == confirmPassword) {
                if (!username.isEmpty() && !email.isEmpty()) {

                    lifecycleScope.launch {
                        RequestCreateUser.sendRequest(username, email, confirmPassword)
                        if (RequestCreateUser.sendRequest(
                                username,
                                email,
                                confirmPassword
                            ) != null
                        ) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Creado con éxito",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }else{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Verifique los campos username y email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }else{
                Toast.makeText(
                    this@RegisterActivity,
                    "Las contraseñas no coindicen",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}