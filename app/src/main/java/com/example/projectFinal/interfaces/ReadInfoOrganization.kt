package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ReadInfoOrganization {
    @GET("v1/organizations/{org_id}")
    suspend fun getOrgById(
        @Header("X-Auth-Token") authToken: String,
        @Path("org_id") userId: String
    ): Response<ResponseBody>
}