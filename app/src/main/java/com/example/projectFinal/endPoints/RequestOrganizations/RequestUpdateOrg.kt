package com.example.projectFinal.endPoints.RequestOrganizations

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.UpdateAOrganization
import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestUpdateOrg {
    var code: String = ""
    var value = ""

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

        val apiService = retrofit.create(UpdateAOrganization::class.java)
        val authToken = GlobalVariables.getInstance().myXSubjectToken

        val findOrg = GlobalVariables.getInstance().listOrganizationsForUpdate.find { it.id == id }

        val organizationJson = findOrg?.let {
            val organization = Organization(
                name = "FIWARE Security1",
                description = "The FI1WARE Foundation is the legal independent body promoting, augmenting open-source FIWARE technologies",
                website = "https://fiw1are.org"
            )
            Gson().toJson(mapOf("organization" to organization))
        }

        println("UPDATE ORGANIZACION")
        if (findOrg != null) {
            val unescapedJsonString = organizationJson?.replace("\\", "")
            val jsonElement = JsonParser.parseString(unescapedJsonString)
            val jsonObject = jsonElement.asJsonObject
            val response = apiService.postData(authToken, id, jsonObject)
            val responseBody = response.body()
            code = response.code().toString()
            val jsonBody = responseBody?.string()
            println("Body despues de actualizarlo: $jsonBody")

            println("RESPONSE RequestUpdateOrg: $response")

            if (responseBody != null) {
                println("Code RequestUpdateOrg: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        } else {
            println("Usuario no encontrado")
        }

    }

    data class Organization(
        val name: String,
        val description: String,
        val website: String
    )


}