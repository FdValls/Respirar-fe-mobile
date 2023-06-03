package ar.edu.ort.requestexamples.data

import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object TrustAllCerts : X509TrustManager {
    override fun checkClientTrusted(
        chain: Array<out java.security.cert.X509Certificate>?,
        authType: String?
    ) {
    }

    override fun checkServerTrusted(
        chain: Array<out java.security.cert.X509Certificate>?,
        authType: String?
    ) {
    }

    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()

    fun createSSLSocketFactory(): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(this)
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())
        return sslContext.socketFactory
    }
}