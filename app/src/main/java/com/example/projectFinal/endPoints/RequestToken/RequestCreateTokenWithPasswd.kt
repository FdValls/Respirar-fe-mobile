package com.example.projectFinal.endPoints.RequestToken

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.utils.UserDataLogin
import ar.edu.ort.requestexamples.interfaces.CreateTokenWithPasswd
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.utils.TokenClass.Companion.assignValueToGlobalVariable
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.RequestBody
import retrofit2.Retrofit

class RequestCreateTokenWithPasswd {

    var code : String = ""

    suspend fun sendRequest(email: String, passwd: String): String {

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

        val createTokenWithPasswd = retrofit.create(CreateTokenWithPasswd::class.java)

        val userDataLogin = UserDataLogin(email, passwd)
        val gson = Gson()
        val json = gson.toJson(userDataLogin)
        val headers = mapOf("X-Subject-Token" to "Bearer token")
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

        val response = createTokenWithPasswd.postData(headers, requestBody)

        code = response.code().toString()

        println("Start Method POST RequestCreateTokenWithPasswd")
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                val responseBodyString = responseBody.string()
                println("------------------------------BODY" + responseBodyString)
                println("------------------------------HEADER " + gson.toJson(response.headers()))
                println("------------------------------X-Subject-Token " + response.headers()["X-Subject-Token"])
                var value = response.headers()["X-Subject-Token"].toString()
                assignValueToGlobalVariable(value)


            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }

        println("End Method POST RequestCreateTokenWithPasswd")

        return code
    }

}

