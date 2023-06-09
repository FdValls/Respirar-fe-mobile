package com.example.projectFinal.endPoints.Request

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.AdministratingUsersWithinAnOrganization
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestAdministrationUserOrg {

    var code: String = ""
    var value = ""

    suspend fun sendRequest(idUser: String, idOrg: String) {

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

        val apiService = retrofit.create(AdministratingUsersWithinAnOrganization::class.java)
        val authToken = GlobalVariables.getInstance().myXSubjectToken

        val response = apiService.postData(authToken, idUser, idOrg)


        println("Method PUT RequestAdministrationUserOrg")

        code = response.code().toString()
        if (response.isSuccessful) {
            val responseBody = response.body()
            code = response.code().toString()
            val jsonBody = responseBody?.string()
            println("Body RequestAdministrationUserOrg: $jsonBody")

            println("RESPONSE RequestAdministrationUserOrg: $response")

            if (responseBody != null) {
                println("Code RequestAdministrationUserOrg: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        }
    }

    fun returnCode(): String{
        return code
    }
}