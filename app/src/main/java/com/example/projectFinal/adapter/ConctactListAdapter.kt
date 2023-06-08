package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.holders.ContactHolder
import com.example.projectFinal.utils.UserUpdate

class ConctactListAdapter(
    private var contactsList: MutableList<UserUpdate>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<ContactHolder>() {

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_user_list,parent,false)
        return (ContactHolder(view))
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {

//        contactsList[position].nombre?.let { holder.setName(it) }
        contactsList[position].email?.let { holder.setEmail(it) }
//        contactsList[position].urlGravatar?.let { holder.setGravatar(it) }


        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }
    }

    fun setData(newData: ArrayList<UserUpdate>) {
        this.contactsList = newData
        this.notifyDataSetChanged()
    }
}