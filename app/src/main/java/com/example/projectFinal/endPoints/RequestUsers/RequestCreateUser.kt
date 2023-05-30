package com.example.projectFinal.endPoints.RequestUsers

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.CreateUser
import com.example.projectFinal.utils.CreateUserRequest
import com.example.projectFinal.utils.User
import com.example.projectFinal.utils.UserDto
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestCreateUser {

    var dtoUser : UserDto? = null

    suspend fun sendRequest(username: String, email: String, passwd: String): UserDto? {

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

        val apiService = retrofit.create(CreateUser::class.java)

        val authToken = GlobalVariables.getInstance().myXSubjectToken
        val user = User("alice", "aszqdzxcxzc@test.com", "test")
        val createUserRequest = CreateUserRequest(user)

        val response = apiService.postData(authToken, createUserRequest)

        println("RESPONSE Creating User: $response")

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                println("Code Creating User: ${response.code()}")
                val responseBodyString = responseBody.string()
                println("RESPONSE Creating User: ${response.body()}")

                //Parsear a JSON para obtener los datos
                val gson = Gson()
                val jsonObject = gson.fromJson(responseBodyString, JsonObject::class.java)
                val userObject = jsonObject.getAsJsonObject("user")
                val username = userObject.get("username").asString
                dtoUser = UserDto(
                    userObject.get("id").asString,
                    userObject.get("username").asString,
                    userObject.get("email").asString,
                    userObject.get("enabled").asBoolean,
                    userObject.get("gravatar").asBoolean,
                    userObject.get("date_password").asString,
                    userObject.get("description")?.asString,
                    userObject.get("website")?.asString,
                )
            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
        return dtoUser
    }
}