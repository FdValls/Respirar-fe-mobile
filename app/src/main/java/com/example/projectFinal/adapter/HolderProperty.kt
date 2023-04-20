package com.example.projectFinal.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectFinal.R
import com.example.projectFinal.entities.Property
import com.example.projectFinal.entities.User
import java.io.Serializable

class HolderProperty(v: View) : RecyclerView.ViewHolder(v), Serializable {

    private var view: View
    private lateinit var photo: ImageView

    init {
        this.view = v
    }

    fun setNombreProperty(nombre: String) {
        val txt: TextView = view.findViewById(R.id.txt_nombre_property)
        txt.text = "Nombre: "+nombre
    }

    fun setDireccionProperty(direccion: String) {
        val txt: TextView = view.findViewById(R.id.txt_direccion_property)
        txt.text = "Dirección: "+direccion
    }

    fun setDuenioProperty(u: User) {
        val txt: TextView = view.findViewById(R.id.txt_duenio_property)
        txt.text = "Dueño: "+u.email
    }

    fun setImgProperty(p: Property) {
        photo = view.findViewById(R.id.id_img_property)
        val img: ImageView = view.findViewById(R.id.id_img_property)
        Glide.with(photo.context).load(p.urlImage).into(photo)
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_package_item_property)
    }
}