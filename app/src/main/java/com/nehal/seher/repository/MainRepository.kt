package com.nehal.seher.repository

import com.nehal.seher.retrofit.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getSurahs()
    suspend fun getParas() = apiHelper.getParas()
}