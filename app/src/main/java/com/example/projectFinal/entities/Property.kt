package com.example.projectFinal.entities

class Property(
    name: String,
    address: String,
    user: User,
    urlImage: String
) {

    var name: String
    var address: String
    var security: Boolean = false
    var user: User
    var urlImage: String

    init {
        this.name = name
        this.address = address
        this.security = security
        this.user = user
        this.urlImage = urlImage
    }

    fun changeSecurity() {
        this.security = true
    }

}