package com.example.projectFinal.endPoints.RequestOrganizations

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.RequestUsers.RequestCreateUser
import com.example.projectFinal.interfaces.CreateOrganization
import com.example.projectFinal.utils.CreateOrganizationRequest
import com.example.projectFinal.utils.Organization
import com.example.projectFinal.utils.OrganizationData
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestCreateOrganization {

    var code: String = ""
    var succesfull: Boolean = false
    var value = ""

    suspend fun sendRequest(name: String, descripcion: String): Boolean {

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

        val apiService = retrofit.create(CreateOrganization::class.java)

        val header = GlobalVariables.getInstance().myXSubjectToken

        val authToken = header
        val organizationData =
            OrganizationData(name, descripcion)
        val createOrgRequest = CreateOrganizationRequest(organizationData)

        val response = apiService.postData(authToken, createOrgRequest)

        code = response.code().toString()
        println("RESPONSE RequestCreateOrganization $response")

        if (response.isSuccessful) {
            succesfull = response.isSuccessful
            val responseBody = response.body()
            if (responseBody != null) {
                println("Code Creating Organizacion: $code")
                val responseBodyString = responseBody.string()

                //Parsear a JSON para obtener los datos
                val gson = Gson()
                val jsonObject = gson.fromJson(responseBodyString, JsonObject::class.java)
                val orgObject = jsonObject.getAsJsonObject("organization")
                val orgName = orgObject.get("name").asString
                println("Nombre organizacion: $orgName")
                val orgDto = Organization(
                    orgObject.get("id").asString,
                    orgObject.get("name").asString,
                    orgObject.get("description").asString,
                    orgObject.get("image").asString,
                    orgObject.get("website")?.asString
                )
                GlobalVariables.getInstance().listOrganizationsForUpdate.add(orgDto)
            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
        return succesfull
    }

    fun retunCodeCreateOrg():String{
        return code
    }


}