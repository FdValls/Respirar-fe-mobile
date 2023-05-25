package com.example.projectFinal.fragments

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.projectFinal.R
import com.example.projectFinal.utils.UserSession

class  Fragment_edit_customer : Fragment() {

    private lateinit var avatarImage: ImageView
    private lateinit var btnSave: Button
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_customer, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatarImage = view.findViewById(R.id.avatar_image)

        val nameText = view.findViewById<TextView>(R.id.nameText)
        val descripcion_text = view.findViewById<TextView>(R.id.descripcion_text)
        val website_text = view.findViewById<TextView>(R.id.website_text)
        val email_text = view.findViewById<TextView>(R.id.email_text)

        btnSave = view.findViewById(R.id.save_button)

        nameText.paintFlags = nameText.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        descripcion_text.paintFlags = descripcion_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        website_text.paintFlags = website_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        email_text.paintFlags = email_text.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        nameText.text = UserSession.userName

      //  val usuario = obtenerUsuarioActual()   //obtener el usuario por el token o similar

       // if (usuario != null) {
         //   when (usuario.tipo) {
         //       "master" -> mostrarCamposEdicionMaster(usuario)
           //     "customer" -> mostrarCamposEdicionCustomer(usuario)
         //       else -> println("Tipo de usuario desconocido")
         //   }
       // }

        Glide.with(this)
            .load("https://www.w3schools.com/howto/img_avatar.png")
            .circleCrop()
            .into(avatarImage)

        btnSave.setBackgroundColor(Color.BLACK)

        btnSave.setOnClickListener {
            var a = Fragment_edit_customerDirections.actionFragmentEditCustomer2ToFragmentCustomer()
            v.findNavController().navigate(a)
        }
    }
/*
    private fun mostrarCamposEdicionMaster(usuario: Usuario) {
        // Configurar campos específicos para usuario "master"
        // Aquí puedes realizar acciones como asignar valores a los campos correspondientes
    }

    private fun mostrarCamposEdicionCustomer(usuario: Usuario) {
        // Configurar campos específicos para usuario "customer"
        // Aquí puedes realizar acciones como asignar valores a los campos correspondientes
    }

    data class Usuario(val tipo: String, val nombre: String, val correo: String, val direccion: String)

    private fun obtenerUsuarioActual(): Usuario? {
        // Simulación de obtención del usuario actual desde una fuente de datos (por ejemplo, UserSession)
        val userType = UserSession.userType // Obtén el tipo de usuario desde UserSession
        val userName = UserSession.userName // Obtén el nombre de usuario desde UserSession
        // Obtén otros datos del usuario según sea necesario

        return if (userType != null && userName != null) {
            Usuario(userType, userName, "", "") // Devuelve el objeto Usuario con los datos obtenidos
        } else {
            null
        }
    }*/
}