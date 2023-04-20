package com.example.projectFinal.entities

class Apartment(user: User, floor: Int, cantHambient: Int) {

    var user: User
    var floor: Int
    var cantHambient: Int

    init {
        this.user = user
        this.floor = floor
        this.cantHambient = cantHambient
    }

    fun modifyUser(user: User) {
        this.user = user
    }
}