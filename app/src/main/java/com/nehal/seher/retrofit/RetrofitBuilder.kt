package com.nehal.seher.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {

    private const val BASE_URL = "https://github.com/mdhaider/quraan-sharif/blob/master/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client: OkHttpClient = OkHttpClient.Builder().apply {
        /**addNetworkInterceptor(StethoInterceptor()) */
        addNetworkInterceptor(loggingInterceptor)
        connectTimeout(10, TimeUnit.MINUTES)
        readTimeout(10, TimeUnit.MINUTES)
        writeTimeout(10, TimeUnit.MINUTES)
    }.build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}