package com.nehal.seher.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrof {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    operator fun invoke(baseUrl: String): ApiService {
        val client = OkHttpClient.Builder().apply {
            /**addNetworkInterceptor(StethoInterceptor()) */
            addNetworkInterceptor(loggingInterceptor)
            connectTimeout(10, TimeUnit.MINUTES)
            readTimeout(10, TimeUnit.MINUTES)
            writeTimeout(10, TimeUnit.MINUTES)
        }.build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
