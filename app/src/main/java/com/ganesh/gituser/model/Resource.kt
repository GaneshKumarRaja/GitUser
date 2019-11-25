package com.ganesh.gituser.model


sealed class Resource<out T> {
    class Success<out T : Any>(val data: T) : Resource<T>()
    class Error(val exception: Throwable) : Resource<Nothing>()
}