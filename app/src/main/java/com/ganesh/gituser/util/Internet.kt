package com.ganesh.gituser.util

import android.content.Context
import javax.inject.Inject
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


interface InternetInterface {
    fun isAvailable(): Boolean

}

open class InternetConnection @Inject constructor(private var context: Context) :
    InternetInterface {


    override
    fun isAvailable(): Boolean {
        var result = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }

        } else {

            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }

        }

        return result
    }

}