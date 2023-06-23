package com.example.projectFinal.activities.ui.users

import android.content.Intent
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
import com.example.projectFinal.activities.NavActivity
import com.example.projectFinal.activities.RegisterActivity
import com.example.projectFinal.adapter.ConctactUserDtoListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestListAllUser
import com.example.projectFinal.interfaces.OnViewItemUserListener
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersFragment : Fragment(), OnViewItemUserListener {

    lateinit var v: View
    lateinit var userContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var argListAdapter: ConctactUserDtoListAdapter
    private lateinit var btnCreate: Button

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllUser.sendRequest()

            userContactos.adapter = argListAdapter

            argListAdapter.notifyDataSetChanged()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.item_user, container, false)

        userContactos = v.findViewById(R.id.id_user_contactos)
        btnCreate = v.findViewById(R.id.id_btnUserCreate)

        btnCreate.setOnClickListener {
            val intent =
                Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            RequestListAllUser.sendRequest()
        }

        var users = GlobalVariables.getInstance().listUsers

        userContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        userContactos.layoutManager = linearLayoutManager

        argListAdapter = ConctactUserDtoListAdapter(users,this)

        userContactos.adapter = argListAdapter

        argListAdapter.notifyDataSetChanged()

    }

    override fun onViewItemUserDetail(user: UserDto) {
        val action = UsersFragmentDirections.actionNavUsersToFragmentEditCustomer3(user.id)
        v.findNavController().navigate(action)
    }
}