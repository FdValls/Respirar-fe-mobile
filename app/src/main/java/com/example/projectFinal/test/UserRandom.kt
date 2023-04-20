package com.example.projectFinal.test

import android.util.Log

class UserRandom {

    fun generateUsersRandom(cantidad: Int): Array<String?>? {

        val usersRandom = arrayOfNulls<String>(cantidad)

        val names = arrayOf(
            "Andrea",
            "David",
            "Baldomero",
            "Balduino",
            "Baldwin",
            "Baltasar",
            "Barry",
            "Bartolo",
            "Bartolomé",
            "Baruc",
            "Baruj",
            "Candelaria",
            "Cándida",
            "Canela",
            "Caridad",
            "Carina",
            "Carisa",
            "Caritina",
            "Carlota",
            "Baltazar"
        )
        val lastNames = arrayOf(
            "Gomez",
            "Guerrero",
            "Cardenas",
            "Cardiel",
            "Cardona",
            "Cardoso",
            "Cariaga",
            "Carillo",
            "Carion",
            "Castiyo",
            "Castorena",
            "Castro",
            "Grande",
            "Grangenal",
            "Grano",
            "Grasia",
            "Griego",
            "Grigalva"
        )
        val age = arrayOf(
            22,
            33,
            44,
            17,
            32,
            64,
            38,
            42,
            29,
            39,
            58,
            81,
            90,
            47,
            21
        )

        val phones = arrayOf(
            "54(11)685-37-3530",
            "54(11)237-71-2079",
            "54(11)451-79-0394",
            "54(11)601-14-8592",
            "54(11)559-22-1539",
            "54(11)030-20-0249",
            "54(11)767-87-1630",
            "54(11)954-98-6030",
            "54(11)703-82-4859",
            "54(11)164-84-2015",
            "54(11)056-44-4157",
            "54(11)651-04-4429",
            "54(11)110-50-5706",
            "54(11)079-71-4130",
            "54(11)465-66-9112",
            "54(11)532-69-3173",
            "54(11)045-01-7430",
            "54(11)350-82-6837"
        )

        for (i in 0 until cantidad) {
            usersRandom[i] =
                (names[Math.floor(Math.random() * (names.size - 1 - 0 + 1) + 0)
                    .toInt()] + " "
                        + lastNames[Math.floor(Math.random() * (lastNames.size - 1 - 0 + 1) + 0)
                    .toInt()] + " "
                        + age[Math.floor(Math.random() * (age.size - 1 - 0 + 1) + 0).toInt()] + " "
                        + phones[Math.floor(Math.random() * (phones.size - 1 - 0 + 1) + 0).toInt()]
                        )
        }
        return usersRandom
    }

    fun print(usersCreated: Array<String?>?) {
        for (i in usersCreated!!.indices) {
            Log.d("Users", usersCreated[i].toString())
        }
    }


}