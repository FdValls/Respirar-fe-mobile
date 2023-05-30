package ar.edu.ort.requestexamples.interfaces

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface CreateTokenWithPasswd {
    @POST("v1/auth/tokens")
    suspend fun postData(@HeaderMap headers: Map<String, String>, @Body requestBody: RequestBody): Response<ResponseBody>
}