import ar.edu.ort.requestexamples.data.TrustAllCerts
import ar.edu.ort.requestexamples.entities.MyData
import ar.edu.ort.requestexamples.interfaces.ApiService
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.RequestBody
import retrofit2.Retrofit

class RequestManager {

    var code : String = ""
    var succesfull : Boolean = false

    suspend fun sendRequest(email: String, passwd: String): Boolean {

        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .hostnameVerifier { _, _ -> true }
            .sslSocketFactory(TrustAllCerts.createSSLSocketFactory(), TrustAllCerts)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ip172-18-0-28-chm079o9ec4g00au4plg-3000.direct.labs.play-with-docker.com/") // Reemplaza con la URL de tu endpoint local
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        //HACE LA PEGADA
        //TRAERNOS DEL LABEL USER Y PASS Y PEGARLO ACA
        //USAR LOGICA DE CODE 201 Y SEGUIR
        val myData = MyData(email, passwd)
        val gson = Gson()
        val json = gson.toJson(myData)
        val headers = mapOf("X-Subject-Token" to "Bearer token")
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), json)

        val response = apiService.postData(headers, requestBody)

        code = response.code().toString()


        retunCode()

        if (response.isSuccessful) {
            succesfull = response.isSuccessful
            val responseBody = response.body()
            if (responseBody != null) {
                val responseBodyString = responseBody.string()
                println("------------------------------BODY" + responseBodyString)
                println("------------------------------HEADER" + gson.toJson(response.headers()))
            } else {
                println("Empty response body")
            }
        } else {
            println("Request failed: ${response.code()}")
        }
        return succesfull
    }

    fun retunCode(): String {
       return code
    }

}

