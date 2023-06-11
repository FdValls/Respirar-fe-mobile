package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.holders.UserEditHolder
import com.example.projectFinal.utils.UserUpdate

class UserEditAdapter(
    //Que lista va?
   private var userList: MutableList<UserUpdate>,
    val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<UserEditHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserEditHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_edit_customer, parent, false)
        return UserEditHolder(view)
    }

    override fun onBindViewHolder(holder: UserEditHolder, position: Int) {
        //val user = userList[position]
        //holder.setName(user.username)
        // holder.setDescripcion(user.description)
        // holder.setWebside(user.website)
        // holder.setEmail(user.email)
        //  user.name?.let { holder.setName(user.username)}
        // user.name?.let { holder.setName(user.username)}
        // user.name?.let { holder.setName(user.username)}
        //user.name?.let { holder.setName(user.username)}
        //contactsList[position].urlGravatar?.let { holder.setGravatar(it) }

        //  holder.getCardLayout().setOnClickListener{
        //   onItemClick(position)
        // }
       }

 override fun getItemCount(): Int {
    return userList.size
 }

/* fun tData(newData: ArrayList<UserBodyRequest>) {
    this.userList = newData
     this.notifyDataSetChanged()
 }*/

}