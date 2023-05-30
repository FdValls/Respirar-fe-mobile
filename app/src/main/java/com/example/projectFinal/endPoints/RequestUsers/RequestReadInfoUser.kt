package com.example.projectFinal.endPoints.RequestUsers

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.ReadInformationAboutUser
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestReadInfoUser {

    suspend fun sendRequest(id: String) {

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

        val apiService = retrofit.create(ReadInformationAboutUser::class.java)

        val response = apiService.getUserById(GlobalVariables.getInstance().myXSubjectToken, id)

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                println("Code RequestReadInfoUser: ${response.code()}")
                println("Body RequestReadInfoUser: ${response.body()}")
                val responseBodyString = responseBody.string()

                //Parsear a JSON para obtener los datos
                val gson = Gson()
                val jsonObject = gson.fromJson(responseBodyString, JsonObject::class.java)
                val userObject = jsonObject.getAsJsonObject("user")
                val username = userObject.get("username").asString
                println("Username RequestReadInfoUser: $username")
            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
    }
}