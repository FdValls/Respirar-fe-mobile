package com.example.projectFinal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.IdOrgUser
import com.example.projectFinal.activities.ui.organization.OrganizationFragmentDirections
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.RequestListAllOrganization
import com.example.projectFinal.endPoints.RequestUsers.RequestListAllUser
import com.example.projectFinal.holders.OrgHolder
import com.example.projectFinal.utils.Organization
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrgListAdapter (
    private var orgList: MutableList<Organization>,
    val onItemClickListener: (Int) -> IdOrgUser
) : RecyclerView.Adapter<OrgHolder>() {

    private lateinit var view: View
    private lateinit var btnGestionar: Button
    private lateinit var btnVer: Button
    private lateinit var myOrg: IdOrgUser
    private lateinit var roleOrg: TextView
    private var isCardCheck: Boolean = false
    private var onlyButton: Boolean = true
    private lateinit var checkCard: CheckBox
    private lateinit var myMap: MutableMap<String, String>
    var listAux: MutableSet<String> = mutableSetOf()


    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.item_org, parent, false)

        btnGestionar = view.findViewById(R.id.id_btnGestionarOrg)
        btnVer = view.findViewById(R.id.id_btnVerOrg)
        roleOrg = view.findViewById(R.id.idRoleOrg)
        checkCard = view.findViewById(R.id.id_selectOrganization)

        return (OrgHolder(view))
    }


    override fun onBindViewHolder(holder: OrgHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {

            myOrg = onItemClickListener(position)

            myMap = RequestListAllOrganization.returnListOnlyRoleIdOrg()
            holder.setRole(myMap[myOrg.id_org].toString())

            orgList[position].name.let { holder.setName(it) }
            orgList[position].description.let { holder.setDescription(it) }


            if (orgList[position].image != "default") {
                holder.setGravatar(orgList[position].image)
            } else {
                orgList[position].image.let { holder.setGravatar(it) }
            }

//            val checkBox = holder.getCheckBox()
//            checkBox.isChecked = true

            holder.getCardLayout().setOnClickListener {
                onlyButton = false

                val checkBox = holder.getCheckBox()
                myOrg = onItemClickListener(position)
                checkBox.isChecked = !checkBox.isChecked


                if (checkBox.isChecked) {
                    isCardCheck = true
                    myOrg.listAuxDelete.add(myOrg.id_org)
                    holder.getCheckBox().isEnabled = true
                    holder.getCheckBox().setTextColor(Color.BLACK)

                } else {
                    myOrg.listAuxDelete.remove(myOrg.id_org)
                    holder.getCheckBox().setTextColor(Color.GRAY)
                }
                GlobalVariables.getInstance().listOrgDelete = myOrg.listAuxDelete

                println("myOrg.listAuxDeletemyOrg.listAuxDelete ${myOrg.listAuxDelete}")
            }

            // implementar gestionar, si es miembro no permite la asignacion de roles a los miembros
            holder.getCardButtonGestionarLayout().setOnClickListener {
                onlyButton = true
                myOrg = onItemClickListener(position)
                if(onlyButton && myMap[myOrg.id_org].toString() == "owner"){
                    val action2 =
                        OrganizationFragmentDirections.actionNavOrganizationToSwitchOwnerMemberFragment(
                            myOrg.id_org)
                    view.findNavController().navigate(action2)
                }else{
                    Snackbar.make(view, "No tienes permiso, solo administradores", Snackbar.LENGTH_SHORT).show();
                }
                notifyItemChanged(position)
                notifyDataSetChanged()
            }

            holder.getCardButtonVerLayout().setOnClickListener {
                onlyButton = true
                if(onlyButton) {
                    myOrg = onItemClickListener(position)
                    if (myMap[myOrg.id_org].toString() == "owner") {
                        val action3 =
                            OrganizationFragmentDirections.actionNavOrganizationToOrganizationListUsersFragment2(
                                myOrg.id_org
                            )
                        view.findNavController().navigate(action3)
                    } else {
                        Snackbar.make(
                            view,
                            "No tenes autorización para ver los miembros",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}
