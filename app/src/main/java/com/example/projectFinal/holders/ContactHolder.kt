package com.example.projectFinal.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.squareup.picasso.Picasso

class ContactHolder(v: View) : RecyclerView.ViewHolder(v)  {

    private var view: View

    init {
        this.view = v
    }

    fun setName(name: String) {
        val txt: TextView = view.findViewById(R.id.txt_name_item)
        txt.text = name
    }

    fun setEmail(email: String) {
        val txt: TextView = view.findViewById(R.id.id_txtEmailOrg)
        txt.text = email
    }

    fun setRol(rol: String) {
        val txt: TextView = view.findViewById(R.id.txt_rol)
        txt.text = rol
    }

    fun setGravatar(gravatar: String) {
        val imageView = view.findViewById<ImageView>(R.id.gravatarId)
        val imageUrl = gravatar // Reemplaza con la URL de la imagen que deseas mostrar
        Picasso.get()
            .load(imageUrl)
            .into(imageView)
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.id_card_package_item_user)
    }
}