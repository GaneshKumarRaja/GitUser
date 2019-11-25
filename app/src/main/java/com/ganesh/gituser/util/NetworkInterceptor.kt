package com.ganesh.gituser.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class NetworkConnectionInterceptor(private val internet: InternetConnection) : Interceptor {
    private val isOnline: Boolean
        get() {
            return internet.isAvailable()
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline) {
            throw NoConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String? {
        return "No Internet connection"
    }

    override val message: String?
        get() =   "No Internet connection"


}