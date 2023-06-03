package com.example.projectFinal.interfaces

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UpdateAOrganization {
    @Headers("Content-Type: application/json")
    @PATCH("v1/organizations/{org_id}")
    suspend fun postData(
        @Header("X-Auth-Token") authToken: String,
        @Path("org_id") userId: String,
        @Body requestBody:
        JsonObject
    ): Response<ResponseBody>
}