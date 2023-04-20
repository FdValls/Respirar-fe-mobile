package com.example.projectFinal.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.projectFinal.R
import com.example.projectFinal.utils.GoogleClientGso

class Fragment_logout : Fragment() {
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_list_expensas, container, false)

        return v
    }

    override fun onStart() {
        super.onStart()

        GoogleClientGso.googleSignInClient.signOut()
        var a = Fragment_logoutDirections.actionFragmentLogoutToActivityLogin()
        v.findNavController().navigate(a)
    }

}