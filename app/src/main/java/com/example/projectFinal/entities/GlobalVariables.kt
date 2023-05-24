package com.example.projectFinal.entities

import android.app.Application

class GlobalVariables : Application() {

    var myXSubjectToken: String = ""
    var url = "http://ip172-19-0-42-chmmi109ec4g00dlng00-3000.direct.labs.play-with-docker.com/"

    companion object {
        private lateinit var instance: GlobalVariables

        fun getInstance(): GlobalVariables {
            if (!::instance.isInitialized) {
                instance = GlobalVariables()
            }
            return instance
        }
    }

}