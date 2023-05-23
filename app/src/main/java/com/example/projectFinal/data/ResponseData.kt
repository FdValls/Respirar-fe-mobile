package ar.edu.ort.requestexamples.data

import okhttp3.ResponseBody

data class ResponseData(
    val body: ResponseBody,
    val header: Map<String, String>
)
