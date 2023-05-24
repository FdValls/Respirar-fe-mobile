package com.example.projectFinal.entities

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.interfaces.UserInterfaceInformationToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestUserInfoToken {

    suspend fun sendRequest(authToken: String, subjectToken: String): Boolean {
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

        val apiService = retrofit.create(UserInterfaceInformationToken::class.java)

        val headers = mapOf(
            "X-Subject-Token" to subjectToken,
            "X-Auth-Token" to authToken
        )

        val response = apiService.sendRequestWithToken(headers)

        val code = response.code().toString()
        println("Code: $code")

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
//                val responseBodyString = responseBody.string()
//                println("Response Body: $responseBodyString")
            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }

        return response.isSuccessful
    }
}