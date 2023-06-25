package com.example.projectFinal.endPoints.RequestOrganizations

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.DeteleOrganization
import com.example.projectFinal.utils.Organization
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestDeleteOrganization {
    var code: String = ""
    var value = ""
    var org: JsonObject? = null
    var findOrg: Organization? = null

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

        val apiService = retrofit.create(DeteleOrganization::class.java)
        val authToken = GlobalVariables.getInstance().myXSubjectToken

        findOrg = GlobalVariables.getInstance().listOrganizationsForUpdate.find { it.id == id }

        val response = apiService.postData(authToken, id)
        code = response.code().toString()
    }

    fun codeDelete(): String{
        return code
    }


}