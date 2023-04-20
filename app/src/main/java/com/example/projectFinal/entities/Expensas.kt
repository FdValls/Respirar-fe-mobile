package com.example.projectFinal.entities

import java.io.Serializable

class Expensas(mes: String, total: Double, urlImage: String) : Serializable {

    var mes: String = ""

    var total: Double = 0.00

    var urlImage: String = ""

    init {
        this.mes = mes
        this.total = total
        this.urlImage = urlImage
    }
}