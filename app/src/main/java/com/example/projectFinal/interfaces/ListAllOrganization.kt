package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ListAllOrganization {
    @Headers("Content-Type: application/json")
    @GET("v1/organizations")
    suspend fun getAllOrganizations(
        @Header("X-Auth-Token") authToken: String,
    ): Response<ResponseBody>
}