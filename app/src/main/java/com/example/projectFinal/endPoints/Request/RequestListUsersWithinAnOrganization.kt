package com.example.projectFinal.endPoints.Request

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.ListUsersWithinAnOrganization
import com.example.projectFinal.utils.UserUpdate
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestListUsersWithinAnOrganization {

    var code: String = ""
    var value = ""
    lateinit var array: JsonArray

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
            //Parsear a JSON para obtener los datos
            val gson = Gson()
            val jsonObject = gson.fromJson(jsonBody, JsonObject::class.java)
            array = jsonObject.getAsJsonArray("organization_users")
            println("Body RequestListUsersWithinAnOrganization: $array")
            println("RESPONSE RequestListUsersWithinAnOrganization: $response")

            if (responseBody != null) {
                println("Code RequestListUsersWithinAnOrganization: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        }
    }

    fun returnCode(): String{
        return code
    }

    fun returnListUserFiltered(): JsonArray {
        return array
    }
}