package com.example.projectFinal.utils

import com.example.projectFinal.data.GlobalVariables

class TokenClass {
    companion object {
        fun assignValueToGlobalVariable(value: String) {
            val myApplication = GlobalVariables.getInstance()
            myApplication.myXSubjectToken = value
        }
    }
}