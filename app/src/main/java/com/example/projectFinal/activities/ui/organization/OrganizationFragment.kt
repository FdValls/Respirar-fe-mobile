package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.OrgListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestOrganizations.RequestDeleteOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.RequestListAllOrganization
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class OrganizationFragment : Fragment() {

    lateinit var v: View
    lateinit var orgContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var argListAdapter: OrgListAdapter
    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button
    private lateinit var myOrg: String

    companion object {
        fun newInstance() = OrganizationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.fragment_list_org, container, false)

        orgContactos = v.findViewById(R.id.org_contactos)
        btnCreate = v.findViewById(R.id.id_CreateBtnOrg)
        btnDelete = v.findViewById(R.id.id_DeleteBtnOrg)

        return v
    }

    override fun onStart() {
        super.onStart()

        var orgs = GlobalVariables.getInstance().listOrganizationsForUpdate

        orgContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        orgContactos.layoutManager = linearLayoutManager

        println("lista de organizaciones ++++++++++++++++++++" + orgs)

        argListAdapter = OrgListAdapter(orgs){ x ->
            OnItemClickListener(x)
        }

        orgContactos.adapter = argListAdapter

        btnCreate.setOnClickListener {
            val action2 = OrganizationFragmentDirections.actionNavOrganizationToCreateOrgFragment()
            v.findNavController().navigate(action2)
        }

        btnDelete.setOnClickListener{
            lifecycleScope.launch {
                RequestDeleteOrganization.sendRequest(myOrg)
            }
        }
    }

    fun OnItemClickListener (position : Int ) : Boolean{
        println("%%%%%%%%%%%%%%%%%%%%%%%%%%%$position")
        val myOrg = GlobalVariables.getInstance().listOrganizationsForUpdate[position].id
        Snackbar.make(v,myOrg.toString(),Snackbar.LENGTH_SHORT).show()

        return true
    }
}