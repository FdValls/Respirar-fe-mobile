package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.ContactListOrgUsersSwitchAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.Request.RequestAdministrationUserOrg
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.endPoints.Request.RequestReadUserRolesWithinAnOrganization
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class SwitchOwnerMemberFragment : Fragment() {

    lateinit var v: View
    lateinit var usersOrg : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var usersOrgListAdapter: ContactListOrgUsersSwitchAdapter
    private lateinit var orgId: String
    private lateinit var myUser: UserDto
    private lateinit var users: MutableList<UserDto>
    //    private var test1: List<JsonObject> = listOf()
    private var test1: MutableList<String> = mutableListOf()
    private lateinit var ids: Ids

    companion object {
        fun newInstance() = SwitchOwnerMemberFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: SwitchOwnerMemberFragmentArgs by navArgs()
        orgId = args.idOrg.toString()

        v = inflater.inflate(R.layout.item_switch_org_user, container, false)

        usersOrg = v.findViewById(R.id.id_orgSwitch_contactos)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crea un objeto DividerItemDecoration
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        // Establece el estilo del separador
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        // Agrega el separador al RecyclerView
        usersOrg.addItemDecoration(itemDecoration)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
    }

    override fun onStart() {
        super.onStart()

        users = GlobalVariables.getInstance().listUsers

        usersOrg.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        usersOrg.layoutManager = linearLayoutManager

        if (!::usersOrgListAdapter.isInitialized) {
            //REVISAR CON EL EQUIPO!
//            CoroutineScope(Dispatchers.Main).launch {
//                users.forEach { element ->
//                    RequestReadUserRolesWithinAnOrganization.sendRequest(element.id,orgId)
//                    println("que quiero buscar? $orgId idUser: ${element.id}")
//                    println("que OBTENGO????? ${RequestReadUserRolesWithinAnOrganization.sendRequest(element.id,orgId)}")
//                    if(RequestReadUserRolesWithinAnOrganization.returnCode() == "200"){
//                        test1.add(RequestReadUserRolesWithinAnOrganization.sendRequest(element.id,orgId))
//                    }
//                }
//                println("TENGO MI LISTA DE USUARIOS CON SUS ROLES??? $test1")
//                println("TENGO MIS usuarios $users")
//                //Hacer una mega lista con todos los usuarios y sus roles
//                usersOrgListAdapter = ContactListOrgUsersSwitchAdapter(users, test1) { x ->
//                    onItemClick(x)
//                }
//                usersOrg.adapter = usersOrgListAdapter
//                usersOrgListAdapter.notifyDataSetChanged()
//            }
            usersOrgListAdapter = ContactListOrgUsersSwitchAdapter(users) { x ->
                onItemClick(x)
            }
            usersOrg.adapter = usersOrgListAdapter
        } else {
            usersOrgListAdapter.notifyDataSetChanged()
        }
    }

    fun onItemClick ( position : Int ) : Ids{
        myUser = users[position]
        println("TENGO MI ID ORG $orgId ++++++++++++ TENGO MI ID USER ${myUser.id}")
        ids = Ids(orgId, myUser.id, position, test1)
        Snackbar.make(v, "Id ${myUser.id}", Snackbar.LENGTH_SHORT).show();
        return ids
    }

    fun mostarRoles(): MutableList<String>{
        return test1
    }

}

data class Ids(val id_org: String, val id_user: String, val position: Int, val test1: MutableList<String>)
