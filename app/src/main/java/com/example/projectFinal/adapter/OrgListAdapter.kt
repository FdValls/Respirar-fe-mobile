package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.holders.OrgHolder
import com.example.projectFinal.utils.Organization

class OrgListAdapter (
    private var orgList: MutableList<Organization>,
    val onItemClickListener: (Int) -> Boolean
) : RecyclerView.Adapter<OrgHolder>() {

    private lateinit var checkBox: CheckBox

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_org,parent,false)

        checkBox = view.findViewById(R.id.id_selectOrganization)

        return (OrgHolder(view))
    }

    override fun onBindViewHolder(holder: OrgHolder, position: Int) {

        orgList[position].name.let { holder.setName(it) }
        orgList[position].description.let { holder.setDescription(it) }
        if(orgList[position].image != "default"){
            holder.setGravatar(orgList[position].image)
        }else{
            orgList[position].image.let { holder.setGravatar(it) }
        }

        holder.getCardLayout().setOnClickListener{
            holder.setCheckBox(!holder.getCheckBox().isChecked)
            onItemClickListener(position)
        }
    }
}
