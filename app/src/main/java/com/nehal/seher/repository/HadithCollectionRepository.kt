package com.nehal.seher.repository

import com.nehal.seher.room.dbhelper.HadithDatabseHelper

class HadithCollectionRepository(private val hadithDatabseHelper: HadithDatabseHelper) {
    suspend fun getAllCollection() = hadithDatabseHelper.getAllCollection()

    suspend fun getALlBook() = hadithDatabseHelper.getAllBook()
}