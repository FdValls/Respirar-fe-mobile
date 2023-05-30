package com.example.projectFinal.interfaces

import com.example.projectFinal.utils.CreateUserRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ListAllUsers {
    @Headers("Content-Type: application/json")
    @GET("v1/users")
    suspend fun getAllUsers(
        @Header("X-Auth-Token") authToken: String,
    ): Response<ResponseBody>
}