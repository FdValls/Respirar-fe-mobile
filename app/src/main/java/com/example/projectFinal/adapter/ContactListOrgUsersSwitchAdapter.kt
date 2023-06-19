package com.example.projectFinal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.Ids
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.Request.*
import com.example.projectFinal.endPoints.RequestOrganizations.RequestListAllOrganization
import com.example.projectFinal.endPoints.RequestUsers.RequestListAllUser
import com.example.projectFinal.endPoints.RequestUsers.RequestReadInfoUser
import com.example.projectFinal.holders.ContactOrgUsersSwitchHolder
import com.example.projectFinal.holders.OrgHolder
import com.example.projectFinal.utils.Organization
import com.example.projectFinal.utils.UserDto
import com.example.projectFinal.utils.UserFull
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ContactListOrgUsersSwitchAdapter(
    private var contactsList: MutableList<UserDto>,
//    private var test1: MutableList<String>,
    val onItemClick: (Int) -> Ids,
) : RecyclerView.Adapter<ContactOrgUsersSwitchHolder>() {

    private lateinit var view: View
    private lateinit var doc: Ids
    private lateinit var icon: ImageView
    private lateinit var switch: Switch
    private lateinit var role: String
    private var userList: MutableSet<UserFull> = mutableSetOf()
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
            println("test1test1test1test1test1test1"+ test2)
            println("getItemCount()getItemCount()"+ getItemCount())

            val mapRoles : MutableMap<String, String> = mutableMapOf()

            for (jsonObject in test2) {
                val userId = jsonObject.get("user_id").asString
                val role = jsonObject.get("role").asString
                mapRoles[userId] = role
            }

            val values = mapRoles.values.toList()
            val keys = mapRoles.keys.toList()
            println("valuesvaluesvaluesvaluesvalues"+ values.size)
            println("mapRolesmapRolesmapRolesmapRolesmapRoles"+ mapRoles)
            println("contactsListcontactsListcontactsList"+ contactsList)
            println("valuesvaluesvaluesvaluesvaluesvaluesvalues"+ values)
            println("keyskeyskeyskeyskeyskeyskeyskeyskeyskeyskeys"+ keys)
            println("posiciones de las keys "+ keys[3])

//            println("TENGO LOS ROLES PARA MOSTRAR EN HOLDER????"+ test1)

            if(position < contactsList.size-1){
                println("QUE ME TRAE DE KEYS?????"+ keys[position])
                if(contactsList.any { it.id == (keys[position]) }){
                    println("El key ${keys[position]} esta en la lista, asigo su rol")
                    holder.setRole("1")
                }else{
                    holder.setRole("2")
                }

            }
            // ROMPE porque quiero tengo más usuarios que roles par aasignar

        }

        holder.getSwitch().isEnabled = false
        holder.getSwitch().setTextColor(Color.GRAY)

        holder.getSwitch().setOnCheckedChangeListener { _, isChecked ->
            role = organizationsRoles.find { it.get("user_id").asString == doc.id_user }.toString()


            if (isChecked) {
                CoroutineScope(Dispatchers.Main).launch {
                    doc = onItemClick(position)
                    RequestAdministrationUserOrg.sendRequest(doc.id_user, doc.id_org)
                    if (RequestAdministrationUserOrg.returnCode() == "201") {
                        RequestAddUserAsAnOwnerOfAnOrganization.sendRequest(doc.id_user, doc.id_org,"member")
                    }
                }
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    doc = onItemClick(position)
                    RequestAddUserAsAnOwnerOfAnOrganization.sendRequest(doc.id_user, doc.id_org,"owner")
                    if (RequestAddUserAsAnOwnerOfAnOrganization.returnCode() == "201") {
                    }
                }
            }
        }

        holder.getCardLayout().setOnClickListener {
            var checkBox: CheckBox
            CoroutineScope(Dispatchers.Main).launch {
                doc = onItemClick(position)
                println("ME TRAIGO LA ORG PARA AVERIGUAR QUE ROL TIENE EL USUARIO LOGUEADO${doc.id_org}")
                RequestListUsersWithinAnOrganization.sendRequest(doc.id_org)
                val test1 = GlobalVariables.getInstance().myArrayOrgJson.find { it.asJsonObject.get("user_id")?.asString == doc.id_user}
                println("TEST1 TEST1 TEST1 TEST1 TEST1 TEST1 TEST1$test1")

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
