package com.example.projectFinal.endPoints.RequestUsers

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.ListAllUsers
import com.example.projectFinal.utils.UserList
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestListAllUser {
    var code : String = ""
    var succesfull : Boolean = false
    var value = ""
    private lateinit var userList : UserList

    suspend fun sendRequest(): Boolean {

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

        val apiService = retrofit.create(ListAllUsers::class.java)

        val authToken = GlobalVariables.getInstance().myXSubjectToken
        println("authTokenauthTokenauthTokenauthTokenauthToken: $authToken")

        val response = apiService.getAllUsers(authToken)

        code = response.code().toString()
        println("RESPONSE RequestListAllUser: $response")

        if (response.isSuccessful) {
            succesfull = response.isSuccessful
            val responseBody = response.body()
            if (responseBody != null) {
                GlobalVariables.getInstance().listUsers.clear()
                println("Code RequestListAllUser: $code")
                val jsonString = responseBody.string()
                val gson = Gson()
                userList = gson.fromJson(jsonString, UserList::class.java)
                println("Lista usuarios userList: $userList")
                for (user in userList.users) {
                    GlobalVariables.getInstance().listUsers.add(user)
                }
                println("Lista usuarios GLOBAL: ${ GlobalVariables.getInstance().listUsers}")
                println("Lista usuarios jsonString: ${jsonString}")

            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
        return succesfull
    }
    

}