package com.example.projectFinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectFinal.R
import com.example.projectFinal.adapter.ConctactListAdapter
import com.example.projectFinal.entities.User
import com.google.android.material.snackbar.Snackbar

class UserListFragment : Fragment() {
    lateinit var v: View

    lateinit var recContactos : RecyclerView

    var contactos : MutableList<User> = ArrayList<User>()

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var contactoListAdapter: ConctactListAdapter

    companion object {
        fun newInstance() = UserListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v =  inflater.inflate(R.layout.item_user, container, false)

        recContactos = v.findViewById(R.id.rec_contactos)

        return v
    }

    override fun onStart() {
        super.onStart()

        //Creo la Lista Dinamica
        for (i in 1..5) {
            contactos.add(User())
            contactos.add(User())
            contactos.add(User())
            contactos.add(User())
            contactos.add(User())
            contactos.add(User())
            contactos.add(User())
        }

        //Configuración Obligatoria

        recContactos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)

        recContactos.layoutManager = linearLayoutManager


        contactoListAdapter = ConctactListAdapter(contactos) { x ->
            onItemClick(x)
        }

        recContactos.adapter = contactoListAdapter

    }

    fun onItemClick ( position : Int ) : Boolean{
        Snackbar.make(v,position.toString(), Snackbar.LENGTH_SHORT).show()
        return true
    }

}