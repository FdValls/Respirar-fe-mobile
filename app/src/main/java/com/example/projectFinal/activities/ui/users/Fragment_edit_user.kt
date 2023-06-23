package com.example.projectFinal.activities.ui.users

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.projectFinal.R
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestUpdateUser
import com.example.projectFinal.utils.UserDto
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import values.objStrings

class  Fragment_edit_user : Fragment() {

    private lateinit var avatarImage: ImageView
    private lateinit var btnSave: Button
    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var description: EditText
    private lateinit var website: EditText
    private lateinit var isEnable: CheckBox
    private var isEnableEdit: Boolean = false
    private lateinit var userId: String
    private lateinit var myUser: UserDto
    lateinit var v: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_user, container, false)

        val args: Fragment_edit_userArgs by navArgs()
        userId = args.idUser.toString()

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatarImage = view.findViewById(R.id.avatar_image)

        username = view.findViewById(R.id.nameText)
        email = view.findViewById(R.id.email_txt)
        description = view.findViewById(R.id.editTextDescriptionPerson)
        website = view.findViewById(R.id.editTextWebSitePerson)
        btnSave = view.findViewById(R.id.save_button)
        isEnable = view.findViewById(R.id.id_checkIsEnable)

        Glide.with(this)
            .load("https://www.w3schools.com/howto/img_avatar.png")
            .circleCrop()
            .into(avatarImage)

        if(!userId.isEmpty() || !userId.isBlank()){
            myUser = GlobalVariables.getInstance().listUsers.find { it.id == userId }!!
            isEnable.isChecked = myUser.enabled

            isEnable.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    isEnableEdit = true
                }
            }

            username.setText(myUser.username)
            email.setText(myUser.email)
            description.setText(myUser.description)
            website.setText(myUser.website)
        }
        btnSave = view.findViewById(R.id.save_button)

        btnSave.setBackgroundColor(Color.BLACK)

        btnSave.setOnClickListener {
            lifecycleScope.launch {
                var userDataChanged = userDidntEdit();
                if (userDataChanged &&checkForNotEmptyFields()) {
                    RequestUpdateUser.sendRequest(userId, username.text.toString(),email.text.toString(), isEnableEdit,myUser.gravatar,myUser.date_password,description.text.toString(), website.text.toString())
                    if(RequestUpdateUser.returnCodeUpdateOUser() == "200" || RequestUpdateUser.returnCodeUpdateOUser() == "201"){
                        Toast.makeText(
                            requireActivity(),
                            objStrings.update_successfully,
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Snackbar.make(view, objStrings.cant_update, Snackbar.LENGTH_SHORT).show();
                    }
                }
                if (checkForNotEmptyFields()) findNavController().popBackStack()
            }
        }
    }

    fun checkForNotEmptyFields(): Boolean {
        var emptyField = "";
        var notEmptyFileds = true;
        if(email.text.toString().isBlank()) emptyField = "email"
        if(username.text.toString().isBlank()) emptyField = "username"
        if(!emptyField.isBlank()){
            notEmptyFileds = false;
            Snackbar.make(view!!, objStrings.cant_update + ", " + emptyField + " can't be empty", Snackbar.LENGTH_SHORT).show();
        }
        return notEmptyFileds;
    }

    fun checkInsertedValues(editText: EditText, myUserField: String?): Boolean{
        var returnValue = false;
        if ( editText.text.toString() != myUserField ) {
            var editTextModifiedOne = (myUserField != null || myUserField != " ") && editText.text.toString().isBlank();
            var editTextModifieTwo = (myUserField == null || myUserField == " ") && !editText.text.toString().isBlank();
            var editTextFullChanged = !editTextModifiedOne && !editTextModifieTwo
            if(editTextModifiedOne || editTextModifieTwo || editTextFullChanged){
                returnValue = true
            }
        }
        return returnValue;
    }


    private fun userDidntEdit(): Boolean {
        var userModifiedDescription: Boolean = checkInsertedValues(description, myUser.description)
        var userModifiedWebsite: Boolean = checkInsertedValues(website, myUser.website);
        if (userModifiedDescription && description.text.toString().isBlank()) {
            description.setText(" ")
        }
        if (userModifiedWebsite && website.text.toString().isBlank()) {
            website.setText(" ")
        }

        val userModifiedEmail = myUser.email != email.text.toString()
        val userModifiedUsername = myUser.username != username.text.toString()

        val noChangesToAnyField = !userModifiedDescription && !userModifiedWebsite && !userModifiedEmail && !userModifiedUsername
        val noChangesToNullFields = myUser.description == null && myUser.website == null && noChangesToAnyField

        val shouldCallAPI = !(noChangesToAnyField || (noChangesToNullFields && myUser.description != null && myUser.website != null))

        return shouldCallAPI
    }





}