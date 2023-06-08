package com.example.projectFinal.activities.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.ui.organization.OrganizationFragmentDirections
import com.example.projectFinal.adapter.OrgListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.google.android.material.snackbar.Snackbar

class UsersFragment : Fragment() {

    lateinit var v: View
    lateinit var userContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var argListAdapter: OrgListAdapter
    private lateinit var btnCreate: Button
    private lateinit var btnEdit: Button

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.fragment_user_list, container, false)

        userContactos = v.findViewById(R.id.org_contactos)
        btnEdit = v.findViewById(R.id.id_btnEditar)

        return v
    }

    override fun onStart() {
        super.onStart()

        var orgs = GlobalVariables.getInstance().listOrganizationsForUpdate

        userContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        userContactos.layoutManager = linearLayoutManager

        println("lista de organizaciones ++++++++++++++++++++" + orgs)

        argListAdapter = OrgListAdapter(orgs){ x ->
            OnItemClickListener(x)
        }

        userContactos.adapter = argListAdapter

        btnCreate.setOnClickListener {
            val action2 = OrganizationFragmentDirections.actionNavOrganizationToCreateOrgFragment()
            v.findNavController().navigate(action2)
        }
    }

    fun OnItemClickListener (position : Int ) : Boolean{
        println("%%%%%%%%%%%%%%%%%%%%%%%%%%%$position")
        val myOrg = GlobalVariables.getInstance().listOrganizationsForUpdate[position]
        Snackbar.make(v,myOrg.toString(), Snackbar.LENGTH_SHORT).show()

        return true
    }
}