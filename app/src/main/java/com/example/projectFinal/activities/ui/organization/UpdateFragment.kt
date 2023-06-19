package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestOrganizations.RequestUpdateOrg
import com.example.projectFinal.utils.Organization
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {

    private lateinit var nameOrgText: EditText
    private lateinit var descriptionOrgText: EditText
    private lateinit var webSiteOrgText: EditText
    private lateinit var btnActualizar: Button
    private lateinit var myOrg: Organization
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: UpdateFragmentArgs by navArgs()
        val orgId = args.idOrg
        myOrg = GlobalVariables.getInstance().listOrganizationsForUpdate.find { it.id == orgId }!!

        v = inflater.inflate(R.layout.fragment_update, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameOrgText = view.findViewById(R.id.editTextNameUpdateOrg)
        descriptionOrgText = view.findViewById(R.id.editTextDescriptionUpdateOrg)
        webSiteOrgText = view.findViewById(R.id.editTextWebSiteUpdateOrg)
        btnActualizar = view.findViewById(R.id.id_UpdateOkBtnOrg)

        nameOrgText.setText(myOrg.name)
        descriptionOrgText.setText(myOrg.description)
        if(myOrg.website == "default"){
            webSiteOrgText.setText("default")
        }else{
            webSiteOrgText.setText(myOrg.website)
        }

        btnActualizar.setOnClickListener {
            if (!nameOrgText.text.toString().isEmpty() && !descriptionOrgText.text.toString().isEmpty()) {
                myOrg.name = nameOrgText.text.toString()
                myOrg.description = descriptionOrgText.text.toString()
                myOrg.website = webSiteOrgText.text.toString()
                lifecycleScope.launch {
                    RequestUpdateOrg.sendRequest(myOrg.id)
                    if(RequestUpdateOrg.retunCodeUpdateOrg() == "201"){
                        Toast.makeText(
                            requireActivity(),
                            "Update con exito",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    findNavController().popBackStack()
                }
            }
        }
    }

}