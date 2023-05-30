package com.example.projectFinal.interfaces

import com.example.projectFinal.utils.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ReadInformationAboutUser {
    @GET("v1/users/{user_id}")
    suspend fun getUserById(
        @Header("X-Auth-Token") authToken: String,
        @Path("user_id") userId: String
    ): Response<ResponseBody>
}