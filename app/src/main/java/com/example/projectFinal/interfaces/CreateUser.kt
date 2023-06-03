package com.example.projectFinal.interfaces

import com.example.projectFinal.utils.CreateUserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CreateUser {
    @Headers("Content-Type: application/json")
    @POST("v1/users")
    suspend fun postData(
        @Header("X-Auth-Token") authToken: String,
        @Body requestBody: CreateUserRequest
    ): Response<ResponseBody>
}