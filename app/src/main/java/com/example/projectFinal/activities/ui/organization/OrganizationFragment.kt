package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.OrgListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestOrganizations.RequestDeleteOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.RequestListAllOrganization
import com.example.projectFinal.endPoints.RequestUsers.RequestListAllUser
import com.example.projectFinal.utils.Organization
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class OrganizationFragment : Fragment() {

    lateinit var v: View
    lateinit var orgContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orgListAdapter: OrgListAdapter
    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var myOrg: Organization
    private lateinit var users: MutableList<UserDto>
    private lateinit var myUser: UserDto
    private lateinit var myOrgId: String
    private lateinit var ids: IdOrgUser
    var listAux : MutableSet<String> = mutableSetOf()
    var listAuxDelete : MutableSet<String> = mutableSetOf()

    companion object {
        fun newInstance() = OrganizationFragment()
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v =  inflater.inflate(R.layout.fragment_list_org, container, false)

        orgContactos = v.findViewById(R.id.org_contactos)
        btnCreate = v.findViewById(R.id.id_UpdateOkBtnOrg)
        btnDelete = v.findViewById(R.id.id_DeleteBtnOrg)
        btnUpdate = v.findViewById(R.id.id_UpdateBtnOrg)

        return v
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()
        }

        var orgs = GlobalVariables.getInstance().listOrganizationsForUpdate

        users = GlobalVariables.getInstance().listUsers


        orgContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        orgContactos.layoutManager = linearLayoutManager

        orgListAdapter = OrgListAdapter(orgs){ x ->
            OnItemClickListener(x)
        }

        orgContactos.adapter = orgListAdapter

        btnCreate.setOnClickListener {
            val action2 = OrganizationFragmentDirections.actionNavOrganizationToCreateOrgFragment()
            v.findNavController().navigate(action2)
        }

        btnDelete.setOnClickListener{
            lifecycleScope.launch {
//                GlobalVariables.getInstance().listOrgDelete.forEach { item ->
//                    val orgDelete = GlobalVariables.getInstance().listOrgDelete.find { it == item }
//                    while (GlobalVariables.getInstance().listOrganizationsForUpdate.contains(orgDelete)) {
//                        GlobalVariables.getInstance().listOrganizationsForUpdate.remove(orgDelete)
//                        if (orgDelete != null) {
//                            RequestDeleteOrganization.sendRequest(orgDelete.id)
//                        }
//                    }
//                }
                GlobalVariables.getInstance().listOrgDelete.forEach { element ->
                    RequestDeleteOrganization.sendRequest(element)
                }
                println("listAuxDeletelistAuxDelete??????? ${GlobalVariables.getInstance().listOrgDelete}")
                orgListAdapter.notifyDataSetChanged()
                val codeDelete = RequestDeleteOrganization
                if(codeDelete.codeDelete() == "204"){
                    Toast.makeText(
                        requireContext(),
                        "Delete: ${myOrg.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(
                        requireContext(),
                        "Error, no se pudo borrar la organizacion",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnUpdate.setOnClickListener{
            val action = OrganizationFragmentDirections.actionNavOrganizationToUpdateFragment(myOrgId)
            v.findNavController().navigate(action)
        }

    }

    fun OnItemClickListener (position : Int ) : IdOrgUser{
        myOrg = GlobalVariables.getInstance().listOrganizationsForUpdate[position]
        myUser = users[position]
        myOrgId = myOrg.id

        println("ID ORG???????????????????????????? ${myOrg.id}")
        println("arranca vacia???? $listAux")


        if (listAux.contains(myOrgId)) {
            listAux.remove(myOrgId)
        } else {
            listAux.add(myOrgId)
        }

        println("ID GUARDADOS ORG ID GUARDADOS ORG ID GUARDADOS ORG???????????????????????????? $listAux")

        ids = IdOrgUser(myOrgId, listAuxDelete)

        listAux.clear()

        return ids
    }
}

//data class IdOrgUser(val id_org: String, val id_user: String, val position: Int)
data class IdOrgUser(val id_org: String, val listAuxDelete: MutableSet<String>)


