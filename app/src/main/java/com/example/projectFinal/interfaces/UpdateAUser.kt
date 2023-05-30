package com.example.projectFinal.interfaces

import com.example.projectFinal.utils.UpdateUser
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UpdateAUser {
    @Headers("Content-Type: application/json")
    @PATCH("v1/users/{user_id}")
    suspend fun postData(
        @Header("X-Auth-Token") authToken: String,
        @Path("user_id") userId: String,
        @Body requestBody: UpdateUser?
    ): Response<ResponseBody>
}