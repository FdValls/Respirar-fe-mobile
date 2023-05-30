package com.example.projectFinal.utils

data class UserDto(
    var id: String,
    var username: String,
    var email: String,
    var enabled: Boolean,
    var gravatar: Boolean,
    var date_password: String,
    var description: String?,
    var website: String?
)

data class UserList(
    var users: List<UserDto>
)