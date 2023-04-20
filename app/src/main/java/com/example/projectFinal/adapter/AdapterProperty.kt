package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.entities.Property

class AdapterProperty(
    private var propertyList: MutableList<Property>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<HolderProperty>() {

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderProperty {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        return (HolderProperty(view))
    }

    override fun onBindViewHolder(holder: HolderProperty, position: Int) {

        holder.setNombreProperty(propertyList[position].name)
        holder.setDireccionProperty(propertyList[position].address)
        holder.setDuenioProperty(propertyList[position].user)
        holder.setImgProperty(propertyList[position])

        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }

    }

    fun setData(newData: ArrayList<Property>) {
        this.propertyList = newData
        this.notifyDataSetChanged()
    }


}