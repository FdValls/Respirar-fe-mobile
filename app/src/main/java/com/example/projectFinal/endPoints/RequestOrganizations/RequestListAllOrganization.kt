package com.example.projectFinal.endPoints.RequestOrganizations

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.endPoints.Request.RequestListUsersWithinAnOrganization
import com.example.projectFinal.interfaces.ListAllOrganization
import com.example.projectFinal.utils.OrganizationList
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestListAllOrganization {

    var code: String = ""
    private var map: MutableMap<String, String> = HashMap<String, String>()


    suspend fun sendRequest() {

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

        val apiService = retrofit.create(ListAllOrganization::class.java)

        val authToken = GlobalVariables.getInstance().myXSubjectToken

        val response = apiService.getAllOrganizations(authToken)

        code = response.code().toString()
        println("RESPONSE RequestListAllOrganization: $response")

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                GlobalVariables.getInstance().listOrganizationsForUpdate.clear()
                println("Code RequestListAllOrganization: $code")
                val jsonString = responseBody.string()
                println("BODY RequestListAllOrganization: $jsonString")
                val gson = Gson()
                val orgList = gson.fromJson(jsonString, OrganizationList::class.java)
                for (orgItem in orgList.organizations) {
                    GlobalVariables.getInstance().listOrganizationsForUpdate.add(orgItem.organization)
                    map[orgItem.organization.id] = orgItem.role
                }
                println("Lista de organizaciones1: ${GlobalVariables.getInstance().listOrganizationsForUpdate}")
                println("Lista de organizaciones2: ${jsonString}")

            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
    }

    fun returnListOnlyRoleIdOrg(): MutableMap<String, String> {
        return map
    }

}