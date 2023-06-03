package com.example.projectFinal.utils

data class UserUpdate(
    val username: String,
    val email: String,
    val enabled: Boolean,
    val gravatar: Boolean,
    val date_password: String,
    val description: String?,
    val website: String?,
)

data class UpdateUser(val user: UserUpdate)

