package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import android.util.Log
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import values.objStrings
import values.objStrings.no_id_selected

class UpdateFragment : Fragment() {

    private lateinit var nameOrgText: EditText
    private lateinit var descriptionOrgText: EditText
    private lateinit var webSiteOrgText: EditText
    private lateinit var btnActualizar: Button
    private var myOrg: Organization? = null
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_update, container, false)

        val args: UpdateFragmentArgs by navArgs()
        val orgId = args.idOrg
        myOrg = if(GlobalVariables.getInstance().listOrganizationsForUpdate.size != 0){
            GlobalVariables.getInstance().listOrganizationsForUpdate.find { it.id == orgId }!!
        }else{
            null
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(myOrg != null){
            nameOrgText = view.findViewById(R.id.editTextNameUpdateOrg)
            descriptionOrgText = view.findViewById(R.id.editTextDescriptionUpdateOrg)
            webSiteOrgText = view.findViewById(R.id.editTextWebSiteUpdateOrg)
            btnActualizar = view.findViewById(R.id.id_UpdateOkBtnOrg)

            nameOrgText.setText(myOrg!!.name)
            descriptionOrgText.setText(myOrg!!.description)
            if(myOrg!!.website == "default"){
                webSiteOrgText.setText("default")
            }else{
                webSiteOrgText.setText(myOrg!!.website)
            }

            btnActualizar.setOnClickListener {
                if (!(nameOrgText.text.toString().isEmpty() || descriptionOrgText.text.toString().isEmpty()) && myOrg != null) {
                    lifecycleScope.launch {
                        myOrg!!.name = nameOrgText.text.toString()
                        myOrg!!.description = descriptionOrgText.text.toString()
                        myOrg!!.website = webSiteOrgText.text.toString()
                        RequestUpdateOrg.sendRequest(myOrg!!.id)
                        var move = true;
                        if(RequestUpdateOrg.returnApiResponseMessage() == "Forbidden") {
                            Snackbar.make(view, objStrings.user_not_allow, Snackbar.LENGTH_SHORT).show();
                            move = false;
                        } else if(RequestUpdateOrg.retunCodeUpdateOrg() == "200"){
                            Toast.makeText(
                                requireActivity(),
                                objStrings.update_successfully,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                objStrings.cant_update,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (move) findNavController().popBackStack()
                    }
                } else {
                    Toast.makeText(
                        requireActivity(),
                        objStrings.empty_fields,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }else{
            Toast.makeText(
                requireActivity(),
                objStrings.no_id_selected,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}