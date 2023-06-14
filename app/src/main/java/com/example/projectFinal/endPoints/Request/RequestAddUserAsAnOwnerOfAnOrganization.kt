package com.example.projectFinal.endPoints.Request

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.AddUserAsAnOwnerOfAnOrganization
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestAddUserAsAnOwnerOfAnOrganization {
    var code: String = ""
    var value = ""

    suspend fun sendRequest(idUser: String, idOrg: String, endPointRole: String) {

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

        val apiService = retrofit.create(AddUserAsAnOwnerOfAnOrganization::class.java)
        val authToken = GlobalVariables.getInstance().myXSubjectToken

        val response = apiService.postData(authToken, idUser, idOrg, endPointRole)

        println("Method PUT RequestAddUserAsAnOwnerOfAnOrganization")
        if (response.isSuccessful) {
            val responseBody = response.body()

            code = response.code().toString()
            val gson = Gson()

            val jsonBody = responseBody?.string()
            println("Body RequestAddUserAsAnOwnerOfAnOrganization: $jsonBody")
            val jsonObject = gson.fromJson(jsonBody, JsonObject::class.java)
            val orgObject = jsonObject.getAsJsonObject("user_organization_assignments")

            println("RESPONSE RequestAddUserAsAnOwnerOfAnOrganization: $response ROLE!!! ${orgObject.get("role")}" )
            if (responseBody != null) {
                println("Code RequestAddUserAsAnOwnerOfAnOrganization: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        }
    }

    fun returnCode(): String{
        return code
    }



}