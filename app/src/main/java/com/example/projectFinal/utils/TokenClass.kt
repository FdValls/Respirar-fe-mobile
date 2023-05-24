package com.example.projectFinal.utils

import android.content.Context
import com.example.projectFinal.entities.GlobalVariables

class TokenClass {
    companion object {
        fun assignValueToGlobalVariable(value: String) {
            val myApplication = GlobalVariables.getInstance()
            myApplication.myXSubjectToken = value
        }
    }
}