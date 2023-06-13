package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.holders.ContactUserFullHolder
import com.example.projectFinal.utils.UserFull

class ContactUserFullListAdapter(
    private var contactsList: MutableList<UserFull>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<ContactUserFullHolder>() {

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactUserFullHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_users,parent,false)
        return (ContactUserFullHolder(view))
    }

    override fun onBindViewHolder(holder: ContactUserFullHolder, position: Int) {

        contactsList[position].name?.let { holder.setName(it) }
        contactsList[position].email?.let { holder.setEmail(it) }
        contactsList[position].role?.let { holder.setRol(it) }

        holder.setGravatar("https://es.gravatar.com/userimage/235287149/a4e1bd9ae68b452bd24598975407f6e3?size=original")

        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }
    }

}