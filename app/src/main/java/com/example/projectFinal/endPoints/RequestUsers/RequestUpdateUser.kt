package com.example.projectFinal.endPoints.RequestUsers

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.UpdateAUser
import com.example.projectFinal.utils.UpdateUser
import com.example.projectFinal.utils.UserUpdate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestUpdateUser {
    var code: String = ""
    var value = ""

    suspend fun sendRequest(id: String, username: String,email: String,enabled: Boolean,gravatar: Boolean,date_password: String, description: String?,website: String?,) {

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .hostnameVerifier { _, _ -> true }
            .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), TrustAllCerts)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.getInstance().url) // Reemplaza con la URL de tu endpoint local
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val apiService = retrofit.create(UpdateAUser::class.java)
        val authToken = GlobalVariables.getInstance().myXSubjectToken

        val user = GlobalVariables.getInstance().listUsers.find { it.id == id }
        println("Usuario buscado en la lista global: $user")

//        val userBody = user?.let {
//            UserUpdate(
//                "Hola mundo",
//                "holaMundotdasest1@test.com",
//                it.enabled,
//                it.gravatar,
//                it.date_password,
//                it.description,
//                it.website
//            )
//        }
        val userBody = UserUpdate(username,email,enabled,gravatar,date_password,description,website)

        val updateUser = userBody?.let { UpdateUser(it) }

        if (user != null) {
            val response = apiService.postData(authToken, id, updateUser)
            val responseBody = response.body()
            code = response.code().toString()
            val jsonBody = responseBody?.string()
            println("Body despues de actualizarlo: $jsonBody")

            println("RESPONSE RequestUpdateUser: $response")

            if (responseBody != null) {
                println("Code RequestUpdateUser: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        } else {
            println("Usuario no encontrado")
        }
    }

}