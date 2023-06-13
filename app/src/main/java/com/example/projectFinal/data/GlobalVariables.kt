package com.example.projectFinal.data

import android.app.Application
import com.example.projectFinal.utils.*

class GlobalVariables : Application() {

    var myXSubjectToken: String = ""
    var url = "http://ip172-19-0-24-ci3sr20gftqg00clt1d0-3005.direct.labs.play-with-docker.com/"
    var listUsers = mutableListOf<UserDto>()
    var listOrganizationsForUpdate = mutableListOf<Organization>()
    var userData: String = ""
    var listUsersFull = mutableListOf<UserFull>()

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