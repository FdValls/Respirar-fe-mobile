package com.example.projectFinal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.Ids
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.Request.*
import com.example.projectFinal.holders.ContactOrgUsersSwitchHolder
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import values.objStrings.assigned_user_as_member
import values.objStrings.assigned_user_as_owner
import values.objStrings.delete_member
import values.objStrings.member
import values.objStrings.not_member_or_owner
import values.objStrings.owner


class ContactListOrgUsersSwitchAdapter(
    private var contactsList: MutableList<UserDto>,
    val onItemClick: (Int) -> Ids,
) : RecyclerView.Adapter<ContactOrgUsersSwitchHolder>() {

    private lateinit var view: View
    private lateinit var doc: Ids
    private lateinit var icon: ImageView
    private lateinit var switch: Switch
    private lateinit var role: String
    private var organizationsRoles: List<JsonObject> = listOf()

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
        CoroutineScope(Dispatchers.Main).launch {

            contactsList[position].username.let { holder.setName(it) }
            contactsList[position].email.let { holder.setEmail(it) }

            doc = onItemClick(position)
            RequestListUsersWithinAnOrganization.sendRequest(doc.id_org)

            val test2 = RequestListUsersWithinAnOrganization.returnListJsonObject()
            val mapRoles : MutableMap<String, String> = mutableMapOf()

            for (jsonObject in test2) {
                val userId = jsonObject.get("user_id").asString
                val role = jsonObject.get("role").asString
                mapRoles[userId] = role
            }

            val keys = mapRoles.keys.toList()

            if(keys.contains(contactsList[position].id)){
                val myRole = mapRoles[contactsList[position].id]
                holder.setRole(myRole.toString())
            }

        }

        holder.getSwitch().isEnabled = false
        holder.getSwitch().setTextColor(Color.GRAY)

        holder.getSwitch().setOnCheckedChangeListener { _, isChecked ->
            role = organizationsRoles.find { it.get("user_id").asString == doc.id_user }.toString()


            if (isChecked) {
                CoroutineScope(Dispatchers.Main).launch {
                    doc = onItemClick(position)
                    RequestAdministrationUserOrg.sendRequest(doc.id_user, doc.id_org)
                    Snackbar.make(view, assigned_user_as_member, Snackbar.LENGTH_SHORT).show();
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    doc = onItemClick(position)
                    RequestAddUserAsAnOwnerOfAnOrganization.sendRequest(doc.id_user, doc.id_org,
                        owner)
                    Snackbar.make(view, assigned_user_as_owner, Snackbar.LENGTH_SHORT).show();
                }
            }
        }

        holder.getCardLayout().setOnClickListener {
            var checkBox: CheckBox
            CoroutineScope(Dispatchers.Main).launch {
                doc = onItemClick(position)
                RequestListUsersWithinAnOrganization.sendRequest(doc.id_org)
                val test1 = GlobalVariables.getInstance().myArrayOrgJson.find { it.asJsonObject.get("user_id")?.asString == doc.id_user}

                checkBox = holder.getCheckBox()
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
        }

        icon.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                var roleMember: String
                doc = onItemClick(position)
                RequestReadUserRolesWithinAnOrganization.sendRequest(doc.id_user, doc.id_org)
                if(RequestReadUserRolesWithinAnOrganization.returnCode() != "404"){
                    roleMember = RequestReadUserRolesWithinAnOrganization.returnRole()
                    if (roleMember == owner|| roleMember == member) {
                        RequestRemoveUserFromOrganization.sendRequest(doc.id_user, doc.id_org, roleMember)
                        Snackbar.make(view, delete_member, Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    Snackbar.make(view,not_member_or_owner, Snackbar.LENGTH_SHORT).show();
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
