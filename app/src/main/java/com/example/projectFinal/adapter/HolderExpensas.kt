package com.example.projectFinal.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectFinal.R
import com.example.projectFinal.entities.Expensas
import java.io.Serializable

class HolderExpensas(v: View) : RecyclerView.ViewHolder(v), Serializable {

    private var view: View
    private lateinit var photo: ImageView

    init {
        this.view = v
    }

    fun setMes(mes: String) {
        val txt: TextView = view.findViewById(R.id.txt_mes_product)
        txt.text = mes
    }

    fun setTotal(total: Double) {
        val txt: TextView = view.findViewById(R.id.txt_total_product)
        txt.text = total.toString()
    }

    fun setImg(e: Expensas) {
        photo = view.findViewById(R.id.id_img_expensa)
        val img: ImageView = view.findViewById(R.id.id_img_expensa)
        Glide.with(photo.context).load(e.urlImage).into(photo)
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.card_package_item_expensas)
    }
}