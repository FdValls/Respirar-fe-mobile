package com.example.projectFinal.endPoints.Request

import ar.edu.ort.requestexamples.data.TrustAllCerts
import com.example.projectFinal.data.GlobalVariables
import com.example.projectFinal.interfaces.ListUsersWithinAnOrganization
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestListUsersWithinAnOrganization {

    var code: String = ""
    var value = ""
    var array: JsonArray = JsonArray()
    private var map: MutableMap<String, String> = HashMap<String, String>()
//    private lateinit var userArray: List<JsonObject>
    private var userArray: List<JsonObject> = listOf()

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

        println("Que ID ORG BUSCO???? $idOrg")
        println("Method PUT RequestListUsersWithinAnOrganization")

        code = response.code().toString()

        if (response.isSuccessful) {
            val responseBody = response.body()
            code = response.code().toString()
            val jsonBody = responseBody?.string()
            //Parsear a JSON para obtener los datos
            val gson = Gson()
            val jsonObject = gson.fromJson(jsonBody, JsonObject::class.java)
            println("jsonObjectjsonObjectjsonObjectjsonObjectjsonObject: $jsonObject")

            val jsonArray = jsonObject.getAsJsonArray("organization_users")
            userArray = jsonArray.map { it.asJsonObject }
            val userIDs = userArray.map { it.get("user_id").asString }
            val organizationIDs = userArray.map { it.get("organization_id").asString }
            val roles = userArray.map { it.get("role").asString }

            array = jsonObject.getAsJsonArray("organization_users")
            GlobalVariables.getInstance().myArrayOrgJson = array
            println("jsonArrayjsonArrayjsonArrayjsonArrayjsonArrayjsonArrayjsonArrayjsonArray: $jsonArray")


            if (responseBody != null) {
                println("Code RequestListUsersWithinAnOrganization: $code")
            } else {
                println("Request failed: ${response.code()}")
            }
        }else{
            println("Request failed: ${response.code()}")
        }
    }

    fun returnListUserFiltered(): JsonArray {
        return array
    }

    fun returnListJsonObject(): List<JsonObject> {
        return userArray
    }

    fun returnCode(): String {
        return code
    }

}