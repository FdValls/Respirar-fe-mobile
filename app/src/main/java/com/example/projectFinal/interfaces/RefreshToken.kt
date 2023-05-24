package com.example.projectFinal.interfaces

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RefreshToken {
    @POST("v1/auth/tokens")
    suspend fun postData(
        @Body requestBody: RequestBody
    ): Response<ResponseBody>
}