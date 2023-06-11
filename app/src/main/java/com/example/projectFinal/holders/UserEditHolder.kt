package com.example.projectFinal.holders

import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.squareup.picasso.Picasso

class UserEditHolder(v: View) : RecyclerView.ViewHolder(v) {

    private var view: View

    init {
        this.view = v
    }

    fun setName(name: String) {
        val txt: TextView  = this.view.findViewById<EditText>(R.id.nameText)
        txt.text = name
    }

    fun setDescripcion(name: Editable) {
        val txt: TextView = this.view.findViewById<EditText>(R.id.descripcion_text)
        txt.text = name
    }

    fun setWebside(name: Editable) {
        val txt: TextView = this.view.findViewById<EditText>(R.id.website_text)
        txt.text = name
    }

    fun setEmail(name: Editable) {
        val txt: TextView = this.view.findViewById<EditText>(R.id.email_text)
        txt.text = name
    }

    fun getContainer(): View {
        return this.view.findViewById<View>(R.id.settings_card)
    }

    fun setGravatar(gravatar: String) {
        val imageView = view.findViewById<ImageView>(R.id.gravatarId)
        val imageUrl = gravatar // Reemplaza con la URL de la imagen que deseas mostrar
        Picasso.get()
            .load(imageUrl)
            .into(imageView)
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.fragment_edit_customer)
    }
}