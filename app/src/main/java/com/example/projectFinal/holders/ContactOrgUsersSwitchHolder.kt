package com.example.projectFinal.holders

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R

class ContactOrgUsersSwitchHolder(v: View) : RecyclerView.ViewHolder(v)  {

    private var view: View
    private lateinit var  switchView: Switch
    private lateinit var  role: String
    private lateinit var  checkBox: CheckBox

    init {
        this.view = v
    }

    fun setName(name: String) {
        val txt: TextView = view.findViewById(R.id.id_txtNameSwitchOrg)
        txt.text = name
    }

    fun setEmail(email: String) {
        val txt: TextView = view.findViewById(R.id.id_txtEmailOrg)
        txt.text = email
    }

    fun getCheckBox(): CheckBox {
        checkBox = view.findViewById(R.id.id_checkBoxAddUserOrg)
        return checkBox
    }

    fun setSwitch(role: String) {
        switchView = view.findViewById(R.id.id_switchOrg)
    }

    fun setRole(role: String) {
        val idrole: TextView = view.findViewById(R.id.id_role)
        idrole.text = role
        if(role == "owner"){
            idrole.setTextColor(Color.GREEN )
        }else{
            idrole.setTextColor(Color.BLUE )
        }

    }

    fun getSwitch(): Switch {
        switchView = view.findViewById(R.id.id_switchOrg)
        return switchView
    }

    fun getCardLayout(): CardView {
        return view.findViewById(R.id.id_card_package_item_switchList)
    }
}