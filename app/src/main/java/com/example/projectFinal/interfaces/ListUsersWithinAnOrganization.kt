package com.example.projectFinal.interfaces

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ListUsersWithinAnOrganization {

    @Headers("Content-Type: application/json")
    @GET("v1/organizations/{organization_id}/users")
    suspend fun postData(
        @Header("X-Auth-token") authToken: String,
        @Path("organization_id") orderId: String
    ): Response<ResponseBody>
}