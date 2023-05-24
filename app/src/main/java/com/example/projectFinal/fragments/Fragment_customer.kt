package com.example.projectFinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.projectFinal.R

class Fragment_customer : Fragment() {

    private lateinit var fotoPerfilCustomer: ImageView
    private lateinit var paisPerfilCustomer: TextView
    private lateinit var horaPerfilCustomer: TextView
    private lateinit var tempPerfilCustomer: TextView
    private lateinit var nombreCompleto: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paisPerfilCustomer = view.findViewById(R.id.id_pais_perfil_customer)
        horaPerfilCustomer = view.findViewById(R.id.id_hora_perfil_customer)
        tempPerfilCustomer = view.findViewById(R.id.id_temp_perfil_customer)
        nombreCompleto = view.findViewById(R.id.id_nombre_completo_customer)

    }

}