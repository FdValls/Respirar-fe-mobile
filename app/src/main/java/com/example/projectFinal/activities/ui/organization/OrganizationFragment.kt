package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
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
import values.objStrings.no_id_selected

class OrganizationFragment : Fragment() {

    lateinit var v: View
    lateinit var orgContactos: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var orgListAdapter: OrgListAdapter
    private lateinit var btnCreate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnUpdate: Button
    private lateinit var users: MutableList<UserDto>
    private lateinit var progressBar: ProgressBar

    companion object {
        fun newInstance() = OrganizationFragment()
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllOrganization.sendRequest()

            orgContactos.adapter = orgListAdapter

            progressBar.visibility = View.GONE
        }

        val layoutParams = progressBar.layoutParams
        layoutParams.width = 440 // Nuevo ancho en píxeles
        layoutParams.height = 440 // Nuevo alto en píxeles
        progressBar.layoutParams = layoutParams

        progressBar.visibility = View.VISIBLE
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

        progressBar = v.findViewById(R.id.progressBar)

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
                GlobalVariables.getInstance().listOrgToModify.forEach { element ->
                    RequestDeleteOrganization.sendRequest(element)
                }
                val codeDelete = RequestDeleteOrganization

                if(GlobalVariables.getInstance().listOrgToModify.size == 0){
                    Toast.makeText(
                        requireContext(),
                        no_id_selected,
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (codeDelete.codeDelete() == "204") {
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
            val orgSelectedSize = GlobalVariables.getInstance().listOrgToModify.size
            if (orgSelectedSize == 0){
                Toast.makeText(
                    requireContext(),
                    objStrings.not_org_selected,
                    Toast.LENGTH_SHORT
                ).show()
            } else if(orgSelectedSize > 1) {
                Toast.makeText(
                    requireContext(),
                    objStrings.too_much_org_to_update,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val action =
                    OrganizationFragmentDirections.actionNavOrganizationToUpdateFragment(GlobalVariables.getInstance().listOrgToModify.first())
                v.findNavController().navigate(action)
            }
        }
    }
}

