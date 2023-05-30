package com.example.projectFinal.endPoints.Request

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.ListUsersWithinAnOrganization
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestListUsersWithinAnOrganization {

    var code: String = ""
    var value = ""

    suspend fun sendRequest(idOrg: String) {

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

        val apiService = retrofit.create(ListUsersWithinAnOrganization::class.java)
        val authToken = GlobalVariables.getInstance().myXSubjectToken

        val response = apiService.postData(authToken,idOrg)

        println("Method PUT RequestListUsersWithinAnOrganization")
        if (response.isSuccessful) {
            val responseBody = response.body()
            code = response.code().toString()
            val jsonBody = responseBody?.string()
            println("Body RequestListUsersWithinAnOrganization: $jsonBody")

            println("RESPONSE RequestListUsersWithinAnOrganization: $response")

            if (responseBody != null) {
                println("Code RequestListUsersWithinAnOrganization: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        }
    }
}