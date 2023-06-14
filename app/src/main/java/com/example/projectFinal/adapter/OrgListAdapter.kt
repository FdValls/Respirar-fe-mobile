package com.example.projectFinal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.OrganizationFragmentDirections
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestListAllUser
import com.example.projectFinal.holders.OrgHolder
import com.example.projectFinal.utils.Organization
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrgListAdapter (
    private var orgList: MutableList<Organization>,
    val onItemClickListener: (Int) -> Organization
) : RecyclerView.Adapter<OrgHolder>() {

    private lateinit var view: View
    private lateinit var btnGestionar: Button
    private lateinit var btnVer: Button
    private lateinit var myOrg: Organization
    private var isCardCheck : Boolean = false
    var listAux : MutableSet<String> = mutableSetOf()


    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        view =  LayoutInflater.from(parent.context).inflate(R.layout.item_org,parent,false)

        btnGestionar = view.findViewById(R.id.id_btnGestionarOrg)
        btnVer = view.findViewById(R.id.id_btnVerOrg)

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
            val checkBox = holder.getCheckBox()
            myOrg = onItemClickListener(position)
            checkBox.isChecked = !checkBox.isChecked


            if (checkBox.isChecked) {
//                Snackbar.make(view,"activado",Snackbar.LENGTH_SHORT).show()
                isCardCheck = true
                listAux.add(myOrg.id)
                holder.getCheckBox().isEnabled = true
                holder.getCheckBox().setTextColor(Color.BLACK)

            } else {
                listAux.remove(myOrg.id)
                holder.getCheckBox().setTextColor(Color.GRAY)
            }
        }

        holder.getCardButtonGestionarLayout().setOnClickListener {
            myOrg = onItemClickListener(position)
            CoroutineScope(Dispatchers.Main).launch {
                RequestListAllUser.sendRequest()
            }
            notifyItemChanged(position)
            notifyDataSetChanged()
            val action2 = OrganizationFragmentDirections.actionNavOrganizationToSwitchOwnerMemberFragment(myOrg.id)
            view.findNavController().navigate(action2)

        }

        holder.getCardButtonVerLayout().setOnClickListener {
            myOrg = onItemClickListener(position)

            val action3= OrganizationFragmentDirections.actionNavOrganizationToOrganizationListUsersFragment2(myOrg.id)
            view.findNavController().navigate(action3)
        }
    }

}
