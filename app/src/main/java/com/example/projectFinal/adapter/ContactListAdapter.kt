package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.holders.ContactHolder
import com.example.projectFinal.utils.UserDto
import com.example.projectFinal.utils.UserFull
import com.example.projectFinal.utils.UserUpdate

class ContactListAdapter(
    private var contactsList: MutableList<UserFull>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<ContactHolder>() {

    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_users,parent,false)
        return (ContactHolder(view))
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {

        contactsList[position].userDtoOrg?.let { holder.setName(it) }
        contactsList[position].email?.let { holder.setEmail(it) }

        holder.setGravatar("https://es.gravatar.com/userimage/235287149/a4e1bd9ae68b452bd24598975407f6e3?size=original")

        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }
    }

}