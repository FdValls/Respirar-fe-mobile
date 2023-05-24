package com.example.projectFinal.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.projectFinal.R

class Fragment_admin : Fragment() {

    private lateinit var fotoPerfil: ImageView
    private lateinit var paisPerfil: TextView
    private lateinit var horaPerfil: TextView
    private lateinit var tempPerfil: TextView
    private lateinit var nombreCompleto: TextView
    private lateinit var btbContacto: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paisPerfil = view.findViewById(R.id.id_pais_perfil)
        horaPerfil = view.findViewById(R.id.id_hora_perfil)
        tempPerfil = view.findViewById(R.id.id_temp_perfil)
        nombreCompleto = view.findViewById(R.id.id_nombre_completo_admin)

        btbContacto = view.findViewById(R.id.id_btn_contacto)
        btbContacto.setBackgroundColor(Color.BLACK)


    }

}