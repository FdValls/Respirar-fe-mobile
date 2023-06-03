package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface UserInterfaceInformationToken {
    @Headers("Content-Type: application/json")
    @GET("v1/auth/tokens")
    suspend fun sendRequestWithToken(@HeaderMap headers: Map<String, String>): Response<ResponseBody>
}