package com.example.projectFinal.utils

import com.example.projectFinal.dataStore.DataStoreManager

data class User (val username: String, val email: String, val password: String)

data class CreateUserRequest(val user: User)