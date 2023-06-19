package com.example.projectFinal.holders

import android.net.Uri
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.squareup.picasso.Picasso

class OrgHolder (v: View) : RecyclerView.ViewHolder(v) {

    private var view: View
    private lateinit var checkBox: CheckBox

    init {
        this.view = v
    }

    fun setName(name: String) {
        val txt: TextView = view.findViewById(R.id.id_nameListOrg)
        txt.text = name
    }

    fun setDescription(desc: String) {
        val txt: TextView = view.findViewById(R.id.id_descListOrg)
        txt.text = desc
    }

    fun setCheckBox(checked: Boolean){
        checkBox = view.findViewById(R.id.id_selectOrganization)
        checkBox.isChecked = checked
    }

    fun getCheckBox(): CheckBox {
        checkBox = view.findViewById(R.id.id_selectOrganization)
        return checkBox
    }

    fun setRole(role: String) {
        val editText: TextView = view.findViewById(R.id.idRoleOrg)
        if(role.isEmpty()){
            editText.text = "asd"
        }
        editText.text = role
    }

    fun setGravatar(gravatar: String) {
        if(gravatar == "default"){
            val imageView = view.findViewById<ImageView>(R.id.id_imgListOrg)
            Picasso.get()
                .load(R.drawable.group_icon)
                .into(imageView)
        }else{
            var imageView = view.findViewById<ImageView>(R.id.id_imgListOrg)
            Picasso.get()
                .load("${GlobalVariables.getInstance().url}img/organizations/${gravatar}")
                .into(imageView)
        }
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.id_cardListOrg)
    }

    fun getCardButtonGestionarLayout(): Button {
        return view.findViewById(R.id.id_btnGestionarOrg)
    }

    fun getCardButtonVerLayout(): Button {
        return view.findViewById(R.id.id_btnVerOrg)
    }
}