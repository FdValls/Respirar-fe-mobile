package com.example.projectFinal.utils

data class User (val username: String, val email: String, val password: String)

data class CreateUserRequest(val user: User)