package com.nehal.seher.repository

import com.nehal.seher.retrofit.ApiHelper

class QuraanRepository(private val apiHelper: ApiHelper) {

    suspend fun getAllSurah() = apiHelper.getSurahs()

    suspend fun getAllParas() = apiHelper.getParas()

    suspend fun getAllHadis() = apiHelper.getHadis()
}