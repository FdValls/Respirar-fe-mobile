package com.example.projectFinal.fragments

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.projectFinal.R

class  Fragment_edit_customer : Fragment() {

    private lateinit var avatarImage: ImageView
    private lateinit var btnSave: Button
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_customer, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatarImage = view.findViewById(R.id.avatar_image)

        val nameText = view.findViewById<TextView>(R.id.nameText)
        val passwordText = view.findViewById<TextView>(R.id.password_text)
        btnSave = view.findViewById(R.id.save_button)

        nameText.paintFlags = nameText.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        passwordText.paintFlags = passwordText.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        Glide.with(this)
            .load("https://www.w3schools.com/howto/img_avatar.png")
            .circleCrop()
            .into(avatarImage)

        btnSave.setBackgroundColor(Color.BLACK)

        btnSave.setOnClickListener {
            var a = Fragment_edit_customerDirections.actionFragmentEditCustomer2ToFragmentCustomer()
            v.findNavController().navigate(a)
        }
    }
}