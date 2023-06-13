package com.example.projectFinal.activities.ui.organization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.ContactUserFullListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.endPoints.RequestUsers.RequestReadInfoUser
import com.example.projectFinal.utils.UserDto
import com.example.projectFinal.utils.UserFull
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.coroutines.launch

class OrganizationListUsersFragment : Fragment() {
    lateinit var v: View
    lateinit var userContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var usersListAdapter: ContactUserFullListAdapter
    private lateinit var myUser: UserDto
    private lateinit var orgId: String
    private lateinit var myUserId: String
    private lateinit var array: JsonArray
    private var userList: MutableSet<UserFull> = mutableSetOf()
    private lateinit var image: String

    var listAux : MutableSet<String> = mutableSetOf()

    companion object {
        fun newInstance() = OrganizationListUsersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args: UpdateFragmentArgs by navArgs()
        orgId = args.idOrg.toString()
        //Con este ID me traigo todos los usuarios que tiene ESA ORG!

        v =  inflater.inflate(R.layout.fragment_organization_list_users, container, false)

        userContactos = v.findViewById(R.id.id_userListOrg_contactos)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Crea un objeto DividerItemDecoration
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        // Establece el estilo del separador
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
        // Agrega el separador al RecyclerView
        userContactos.addItemDecoration(itemDecoration)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider))
    }

    override fun onStart(){
        super.onStart()

//        var users = GlobalVariables.getInstance().listUsers
        lifecycleScope.launch {
            RequestListUsersWithinAnOrganization.sendRequest(orgId)
            array = RequestListUsersWithinAnOrganization.returnListUserFiltered()
            val gson = Gson()
            val jsonArray = gson.fromJson(array, JsonArray::class.java)
            for (jsonElement in jsonArray) {
                val jsonObject = jsonElement.asJsonObject
                val userId = jsonObject.get("user_id").asString
                val email = RequestReadInfoUser.sendRequest(userId)?.get("email")?.asString
                image = RequestReadInfoUser.sendRequest(userId)?.get("image")?.asString.toString()
                val role = jsonObject.get("role").asString
                val myName: UserDto? = GlobalVariables.getInstance().listUsers.find { it.id == userId }

                val user = myName?.let { UserFull(userId, email, role, it.username) }
                if (user != null) {
                    userList.add(user)
                }

            }
            userContactos.setHasFixedSize(true)
            linearLayoutManager = LinearLayoutManager(context)

            println("test1test1???????????????????????????? ${GlobalVariables.getInstance().listUsersFull}")

            userContactos.layoutManager = linearLayoutManager

            usersListAdapter = ContactUserFullListAdapter(userList.toMutableList()){ x ->
                OnItemClickListener(x)
            }

            userContactos.adapter = usersListAdapter
        }

    }

    fun OnItemClickListener (position : Int ) : Boolean{
        myUser = GlobalVariables.getInstance().listUsers[position]
        println("ID ORG???????????????????????????? ${myUser.id}")
        myUserId = myUser.id

        Snackbar.make(v,myUserId, Snackbar.LENGTH_SHORT).show()
        listAux.add(myUser.id)
        return true
    }



}