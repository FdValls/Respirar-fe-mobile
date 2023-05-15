package com.example.projectFinal.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.projectFinal.R
import com.example.projectFinal.databinding.ActivityMainBinding
import com.example.projectFinal.databinding.ActivityNavBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var userName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonLogin = binding.loginButton
        val buttonRegister = binding.loginRegister
        userName = binding.username

        buttonLogin.setOnClickListener {
            val intent =
                Intent(applicationContext, NavActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

//    buttonRegister.setOnClickListener
//    {
//        Toast.makeText(
//            this,
//            "Probando boton REGISTER!",
//            Toast.LENGTH_SHORT
//        ).show()
//    }

}