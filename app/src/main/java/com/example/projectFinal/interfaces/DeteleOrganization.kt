package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface DeteleOrganization {
    @Headers("Content-Type: application/json")
    @DELETE("v1/organizations/{org_id}")
    suspend fun postData(
        @Header("X-Auth-token") authToken: String,
        @Path("org_id") userId: String,
    ): Response<ResponseBody>
}