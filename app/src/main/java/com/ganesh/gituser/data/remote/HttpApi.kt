package com.ganesh.gituser.data.remote


import com.ganesh.gituser.model.UserModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface HttpApi {

    @GET("/users/{name}")
    fun getUserDetailsAsync(@Path("name") userName: String): Deferred<UserModel>

}