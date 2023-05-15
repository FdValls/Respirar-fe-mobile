package com.example.projectFinal.entities

import java.security.MessageDigest
import java.util.UUID

class User {

    var id: UUID

    var urlGravatar: String?

    var nombre: String?

    var email: String?

    var habilitar: Boolean?

    init {
        this.id = generarUUID()
        this.nombre = generarNombreRandom()
        this.email = generarEmailRandom()
        this.habilitar = true
        this.urlGravatar = obtenerURLGravatar("fernandodanielvalls@gmail.com")
    }

    // Generamos UUID random

    fun generarUUID(): UUID {
        return UUID.randomUUID()
    }

    fun obtenerURLGravatar(email: String): String {
        val hash = obtenerHashMD5(email.trim().toLowerCase())
        return "https://www.gravatar.com/avatar/$hash"
    }

    private fun obtenerHashMD5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        val hexChars = StringBuilder(digest.size * 2)

        for (byte in digest) {
            val hex = Integer.toHexString(0xFF and byte.toInt())
            if (hex.length == 1) {
                hexChars.append('0')
            }
            hexChars.append(hex)
        }

        return hexChars.toString()
    }

    // Generamos emails random
    fun generarEmailRandom(): String {
        val dominios = listOf("example.com", "gmail.com", "yahoo.com", "hotmail.com", "outlook.com")
        val caracteres = "abcdefghijklmnopqrstuvwxyz1234567890"
        val nombreUsuario = StringBuilder()

        // Generar nombre de usuario aleatorio
        val longitudNombreUsuario = (6..12).random()
        for (i in 0 until longitudNombreUsuario) {
            val indice = (caracteres.indices).random()
            nombreUsuario.append(caracteres[indice])
        }

        // Generar dominio aleatorio
        val indiceDominio = (dominios.indices).random()
        val dominio = dominios[indiceDominio]

        // Combinar nombre de usuario y dominio
        return "$nombreUsuario@$dominio"
    }

    // Generamos nombres random
    fun generarNombreRandom(): String {
        val nombres = listOf(
            "Liam", "Noah", "Oliver", "Elijah", "William", "James", "Benjamin", "Lucas", "Henry", "Alexander",
            "Mia", "Emma", "Olivia", "Sophia", "Ava", "Isabella", "Charlotte", "Amelia", "Harper", "Evelyn"
        )
        val indice = (0 until nombres.size).random()
        return nombres[indice]
    }


}