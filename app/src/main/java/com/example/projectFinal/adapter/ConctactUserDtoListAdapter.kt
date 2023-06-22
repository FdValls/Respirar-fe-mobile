package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestDeleteUser
import com.example.projectFinal.holders.ContactUserDtoHolder
import com.example.projectFinal.interfaces.OnViewItemUserListener
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import values.objStrings.delete_user

class ConctactUserDtoListAdapter (
    private var contactsList: MutableList<UserDto>,
    private val onItemClick: OnViewItemUserListener
) : RecyclerView.Adapter<ContactUserDtoHolder>() {

    private lateinit var view: View
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button
    private var idUser: String = ""

    override fun getItemCount(): Int {
        return contactsList.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactUserDtoHolder {
        view =  LayoutInflater.from(parent.context).inflate(R.layout.fragment_user_list,parent,false)

        btnEdit = view.findViewById(R.id.id_btnEditar)
        btnDelete = view.findViewById(R.id.id_btnDelete)

        return (ContactUserDtoHolder(view))
    }

    override fun onBindViewHolder(holder: ContactUserDtoHolder, position: Int) {

        contactsList[position].username?.let { holder.setName(it) }
        contactsList[position].email?.let { holder.setEmail(it) }

        holder.setGravatar("https://es.gravatar.com/userimage/235287149/a4e1bd9ae68b452bd24598975407f6e3?size=original")

        holder.getCardButtonEditLayout().setOnClickListener{
            onItemClick.onViewItemUserDetail(contactsList[position])
        }

        holder.getCardButtonDeleteLayout().setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                Snackbar.make(view, "$delete_user: ${contactsList[position].username}", Snackbar.LENGTH_SHORT).show();
                val userDelete = GlobalVariables.getInstance().listUsers[position]
                GlobalVariables.getInstance().listUsers.remove(userDelete)
                RequestDeleteUser.sendRequest(contactsList[position].id)
                notifyItemRemoved(position)
            }
        }

    }

}