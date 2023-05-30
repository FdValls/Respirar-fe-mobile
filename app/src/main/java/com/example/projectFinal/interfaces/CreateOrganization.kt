package com.example.projectFinal.interfaces

import com.example.projectFinal.utils.CreateOrganizationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface CreateOrganization {
    @Headers("Content-Type: application/json")
    @POST("v1/organizations")
    suspend fun postData(
        @Header("X-Auth-Token") authToken: String,
        @Body requestBody: CreateOrganizationRequest
    ): Response<ResponseBody>
}
