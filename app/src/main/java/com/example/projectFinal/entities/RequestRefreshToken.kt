package com.example.projectFinal.entities

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.utils.MyData
import ar.edu.ort.requestexamples.interfaces.ApiService
import com.example.projectFinal.interfaces.RefreshToken
import com.example.projectFinal.utils.TokenClass
import com.example.projectFinal.utils.TokenRefresh
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestRefreshToken {
    var code : String = ""
    var succesfull : Boolean = false
    var value = ""

    suspend fun sendRequest(token: String): Boolean {

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

        val apiService = retrofit.create(RefreshToken::class.java)

        val myData = TokenRefresh(token)
        val gson = Gson()
        val json = gson.toJson(myData)
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

        val response = apiService.postData(requestBody)

        code = response.code().toString()

        if (response.isSuccessful) {
            succesfull = response.isSuccessful
            val responseBody = response.body()
            if (responseBody != null) {
                println("Code Refresh token: $code")
            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
        return succesfull
    }
}