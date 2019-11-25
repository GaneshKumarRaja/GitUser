package com.ganesh.gituser.di

import android.app.Application
import com.ganesh.gituser.data.repository.UserInfoRepoUseCase
import com.ganesh.gituser.data.remote.HttpApi
import com.ganesh.gituser.data.repository.UserInfoRepo
import com.ganesh.gituser.util.InternetInterface
import com.ganesh.gituser.util.InternetConnection
import com.ganesh.gituser.util.NetworkConnectionInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule constructor( ) {

    @Singleton
    @Provides
    internal fun provideInternetConnection(app: Application): InternetConnection {
        return InternetConnection(app.applicationContext)
    }

    @Singleton
    @Provides
    internal fun provideRepository(api: HttpApi, internet: InternetConnection): UserInfoRepoUseCase {
        return UserInfoRepo(api, internet)
    }

    @Singleton
    @Provides
    internal fun provideNetworkConnectionInterceptor(internet: InternetConnection): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(internet)
    }


    @Singleton
    @Provides
    fun createHttpClient(networkInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(logging)
            .addInterceptor(networkInterceptor)
        client.readTimeout(5 * 60, TimeUnit.SECONDS)
        return client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.method(original.method, original.body).build()
            return@addInterceptor it.proceed(request)
        }.build()

    }

    @Singleton
    @Provides
    fun provideGithubService(okHttpClient: OkHttpClient): HttpApi {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            .client(okHttpClient)

            .build()
            .create(HttpApi::class.java)
    }


}
