package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.OrgListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestOrganizations.RequestDeleteOrganization
import com.example.projectFinal.endPoints.RequestOrganizations.RequestListAllOrganization
import com.example.projectFinal.utils.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import values.objStrings

class OrganizationFragment : Fragment() {

    lateinit var v: View
    lateinit var orgContactos: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orgListAdapter: OrgListAdapter
    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var users: MutableList<UserDto>
    private lateinit var myOrgId: String

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
        v = inflater.inflate(R.layout.fragment_list_org, container, false)

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

        orgListAdapter = OrgListAdapter(orgs)

        orgContactos.adapter = orgListAdapter

        btnCreate.setOnClickListener {
            val action2 = OrganizationFragmentDirections.actionNavOrganizationToCreateOrgFragment()
            v.findNavController().navigate(action2)
        }

        btnDelete.setOnClickListener {
            lifecycleScope.launch {
                GlobalVariables.getInstance().listOrgDelete.forEach { element ->
                    RequestDeleteOrganization.sendRequest(element)
                }
                orgListAdapter.notifyDataSetChanged()
                val codeDelete = RequestDeleteOrganization
                if (codeDelete.codeDelete() == "204") {
                    Toast.makeText(
                        requireContext(),
                        objStrings.delete_orgs,
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        requireContext(),
                        objStrings.delete_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnUpdate.setOnClickListener {
            val action =
                OrganizationFragmentDirections.actionNavOrganizationToUpdateFragment(myOrgId)
            v.findNavController().navigate(action)
        }
    }
}

