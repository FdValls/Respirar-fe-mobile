package com.example.projectFinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.OrganizationFragmentDirections
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.holders.OrgHolder
import com.example.projectFinal.utils.Organization
import com.google.android.material.snackbar.Snackbar

class OrgListAdapter (
    private var orgList: MutableList<Organization>,
    val onItemClickListener: (Int) -> Boolean
) : RecyclerView.Adapter<OrgHolder>() {

    private lateinit var checkBox: CheckBox
    private lateinit var btnGestionar: Button
    private lateinit var btnVer: Button
    private lateinit var myOrg: Organization

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_org,parent,false)

        checkBox = view.findViewById(R.id.id_selectOrganization)
        btnGestionar = view.findViewById(R.id.id_btnGestionarOrg)
        btnVer = view.findViewById(R.id.id_btnVerOrg)

        btnGestionar.setOnClickListener {
            val action2 = OrganizationFragmentDirections.actionNavOrganizationToSwitchOwnerMemberFragment(myOrg.id)
            println("ID CUANDO MANDO AL FRAGMENT???????????????????????????? ${myOrg.id}")
            view.findNavController().navigate(action2)
        }

        btnVer.setOnClickListener {
            val action3= OrganizationFragmentDirections.actionNavOrganizationToOrganizationListUsersFragment2(myOrg.id)
            view.findNavController().navigate(action3)
            Snackbar.make(view, "Muestro todos los users de esa ORG", Snackbar.LENGTH_SHORT).show();
        }

        return (OrgHolder(view))
    }


    override fun onBindViewHolder(holder: OrgHolder, position: Int) {
        orgList[position].name.let { holder.setName(it) }
        orgList[position].description.let { holder.setDescription(it) }
        if(orgList[position].image != "default"){
            holder.setGravatar(orgList[position].image)
        }else{
            orgList[position].image.let { holder.setGravatar(it) }
        }

        holder.getCardLayout().setOnClickListener{
            myOrg = GlobalVariables.getInstance().listOrganizationsForUpdate[position]
            holder.setCheckBox(!holder.getCheckBox().isChecked)
            onItemClickListener(position)
        }
    }
}
