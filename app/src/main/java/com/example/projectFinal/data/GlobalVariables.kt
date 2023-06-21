package com.example.projectFinal.data

import android.app.Application
import com.example.projectFinal.utils.*
import com.google.gson.JsonArray

class GlobalVariables : Application() {

    var myXSubjectToken: String = ""
    var idGlobalForUpdate: String = ""
    var url = "http://46.17.108.45:3000/"
    var listUsers = mutableListOf<UserDto>()
    var listOrganizationsForUpdate = mutableListOf<Organization>()
    var userData: String = ""
    var listOrgDelete = mutableSetOf<String>()
    var myArrayOrgJson : JsonArray = JsonArray()

    companion object {
        private lateinit var instance: GlobalVariables

        fun getInstance(): GlobalVariables {
            if (!Companion::instance.isInitialized) {
                instance = GlobalVariables()
            }
            return instance
        }
    }
}