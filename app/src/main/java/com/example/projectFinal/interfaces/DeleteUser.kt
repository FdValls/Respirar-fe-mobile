package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface DeleteUser {
    @Headers("Content-Type: application/json")
    @DELETE("v1/users/{user_id}")
    suspend fun postData(
        @Header("X-Auth-token") authToken: String,
        @Path("user_id") userId: String,
    ): Response<ResponseBody>
}