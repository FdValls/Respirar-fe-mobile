package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface UserInterfaceInformationToken {
    @Headers("Content-Type: application/json")
    @GET("URL_NUEVO_ENDPOINT")
    suspend fun sendRequestWithToken(@HeaderMap headers: Map<String, String>): Response<ResponseBody>
}