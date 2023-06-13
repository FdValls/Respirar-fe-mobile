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
    private lateinit var gravatarCheckBox: CheckBox
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var termsCheckBox: CheckBox
    private lateinit var registerBtn: Button
    private var checkEmail by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.nameUserText)
        emailEditText = findViewById(R.id.emailEditText)
        emailEditText = findViewById(R.id.emailEditText)
        gravatarCheckBox = findViewById(R.id.gravatarCheckBox)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        termsCheckBox = findViewById(R.id.termsCheckBox)
        registerBtn = findViewById(R.id.id_registerBtn)

        registerBtn.setOnClickListener {
            val username = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val isGravatarEnabled = gravatarCheckBox.isChecked
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val areTermsAccepted = termsCheckBox.isChecked

            Log.d("Registro", "Username: $username")
            Log.d("Registro", "Email: $email")
            Log.d("Registro", "Gravatar habilitado: $isGravatarEnabled")
            Log.d("Registro", "Contraseña: $password")
            Log.d("Registro", "Confirmar contraseña: $confirmPassword")
            Log.d("Registro", "Términos aceptados: $areTermsAccepted")

            if (password == confirmPassword) {
                if (!username.isEmpty() && !email.isEmpty() && areTermsAccepted) {

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
                        "Verifique los campos username, email y acepte los terminos y condiciones",
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