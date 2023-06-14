package com.example.projectFinal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.Ids
import com.example.projectFinal.endPoints.Request.RequestAddUserAsAnOwnerOfAnOrganization
import com.example.projectFinal.endPoints.Request.RequestAdministrationUserOrg
import com.example.projectFinal.endPoints.Request.RequestRemoveUserFromOrganization
import com.example.projectFinal.endPoints.Request.RequestReadUserRolesWithinAnOrganization
import com.example.projectFinal.holders.ContactOrgUsersSwitchHolder
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactListOrgUsersSwitchAdapter(
    private var contactsList: MutableList<UserDto>,
    val onItemClick: (Int) -> Ids,
) : RecyclerView.Adapter<ContactOrgUsersSwitchHolder>() {

    private lateinit var view: View
    private lateinit var doc: Ids
    private lateinit var icon: ImageView
    private lateinit var switch: Switch


    override fun getItemCount(): Int {
        return contactsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactOrgUsersSwitchHolder {
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_switch_list, parent, false)

        icon = view.findViewById(R.id.icon)
        return (ContactOrgUsersSwitchHolder(view))
    }

    override fun onBindViewHolder(holder: ContactOrgUsersSwitchHolder, position: Int) {

        contactsList[position].username.let { holder.setName(it) }
        contactsList[position].email.let { holder.setEmail(it) }

        holder.getSwitch().isEnabled = false
        holder.getSwitch().setTextColor(Color.GRAY)

        holder.getSwitch().setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                CoroutineScope(Dispatchers.Main).launch {
                    doc = onItemClick(position)
                    RequestAdministrationUserOrg.sendRequest(doc.id_user, doc.id_org)
                    if (RequestAdministrationUserOrg.returnCode() == "201") {
                        Snackbar.make(view, "TEST MEMBER", Snackbar.LENGTH_SHORT).show();
                    }
                }
                println("Switch activado")
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    doc = onItemClick(position)
                    RequestAddUserAsAnOwnerOfAnOrganization.sendRequest(doc.id_user, doc.id_org)
                    if (RequestAddUserAsAnOwnerOfAnOrganization.returnCode() == "201") {
                        Snackbar.make(view, "TEST OWNER", Snackbar.LENGTH_SHORT).show();
                    }
                }
                println("Switch desactivado")
            }
        }

        holder.getCardLayout().setOnClickListener {
            val checkBox = holder.getCheckBox()
            switch = holder.getSwitch()

            checkBox.isChecked = !checkBox.isChecked
            switch.isChecked = checkBox.isChecked

            if (checkBox.isChecked) {
                switch.isEnabled = true
                switch.setTextColor(Color.BLACK)
            } else {
                switch.isEnabled = false
                switch.setTextColor(Color.GRAY)
            }
        }

        icon.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                doc = onItemClick(position)
                RequestReadUserRolesWithinAnOrganization.sendRequest(doc.id_user, doc.id_org)
                val role = RequestReadUserRolesWithinAnOrganization.returnRole()
                if (role == "owner" || role == "member") {
                    RequestRemoveUserFromOrganization.sendRequest(doc.id_user, doc.id_org, role)
                    if (RequestRemoveUserFromOrganization.returnCode() == "204") {
                        Snackbar.make(view, "Borro miembro", Snackbar.LENGTH_SHORT).show();
                    }
                }
                val checkBox = holder.getCheckBox()
                switch = holder.getSwitch()

                checkBox.isChecked = false
                switch.isEnabled = false
                switch.setTextColor(Color.GRAY)
            }

        }

    }
}
