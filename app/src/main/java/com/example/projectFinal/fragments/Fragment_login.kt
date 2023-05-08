package com.example.projectFinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.projectFinal.R

class Fragment_login : Fragment() {

    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonLogin = view.findViewById<Button>(R.id.loginButton)
        val buttonRegister = view.findViewById<Button>(R.id.loginRegister)

        buttonLogin.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Probando boton LOGIN!",
                Toast.LENGTH_SHORT
            ).show()

        }

        buttonRegister.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Probando boton REGISTER!",
                Toast.LENGTH_SHORT
            ).show()
        }



    }

}