package com.example.projectFinal.activities.ui.users

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.activities.NavActivity
import com.example.projectFinal.activities.RegisterActivity
import com.example.projectFinal.adapter.ConctactUserDtoListAdapter
import com.example.projectFinal.data.GlobalVariables
import com.google.android.material.snackbar.Snackbar

class UsersFragment : Fragment() {

    lateinit var v: View
    lateinit var userContactos : RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var argListAdapter: ConctactUserDtoListAdapter
    private lateinit var btnCreate: Button
    var listAux : MutableSet<String> = mutableSetOf()
    private lateinit var myUserId: String

    companion object {
        fun newInstance() = UsersFragment()
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

        var users = GlobalVariables.getInstance().listUsers

        userContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        userContactos.layoutManager = linearLayoutManager


        argListAdapter = ConctactUserDtoListAdapter(users){ x ->
            onItemClick(x)
        }

        userContactos.adapter = argListAdapter

        argListAdapter.notifyDataSetChanged()

    }

    fun onItemClick ( position : Int ) : Boolean{
        myUserId = GlobalVariables.getInstance().listUsers[position].id
        listAux.add(myUserId)
        Snackbar.make(v , "Clickeo en el card???", Snackbar.LENGTH_SHORT).show()
        return true
    }
}