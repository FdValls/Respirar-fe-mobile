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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrganizationFragment : Fragment() {

    lateinit var v: View
    lateinit var orgContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orgListAdapter: OrgListAdapter
    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var myOrg: Organization
    private lateinit var myOrgId: String
    var listAux : MutableSet<String> = mutableSetOf()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crea un objeto DividerItemDecoration
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        // Establece el estilo del separador
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        // Agrega el separador al RecyclerView
        orgContactos.addItemDecoration(itemDecoration)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()
        }

        var orgs = GlobalVariables.getInstance().listOrganizationsForUpdate

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
                listAux.forEach { item ->
                    val orgDelete = GlobalVariables.getInstance().listOrganizationsForUpdate.find { it.id == item }
                    while (GlobalVariables.getInstance().listOrganizationsForUpdate.contains(orgDelete)) {
                        GlobalVariables.getInstance().listOrganizationsForUpdate.remove(orgDelete)
                        if (orgDelete != null) {
                            RequestDeleteOrganization.sendRequest(orgDelete.id)
                        }
                    }
                }
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

    fun OnItemClickListener (position : Int ) : Organization{
        myOrg = GlobalVariables.getInstance().listOrganizationsForUpdate[position]
        println("ID ORG???????????????????????????? ${myOrg.id}")
        myOrgId = myOrg.id

        Snackbar.make(v,myOrgId,Snackbar.LENGTH_SHORT).show()
        listAux.add(myOrg.id)
        println("ID GUARDADOS ORG ID GUARDADOS ORG ID GUARDADOS ORG???????????????????????????? ${listAux}")
        return myOrg
    }
}