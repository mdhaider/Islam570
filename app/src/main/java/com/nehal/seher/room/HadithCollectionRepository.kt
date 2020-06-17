package com.nehal.seher.room

class HadithCollectionRepository(private val hadithDatabseHelper: HadithDatabseHelper) {
    suspend fun getAllCollection() = hadithDatabseHelper.getAllCollection()

    suspend fun getALlBook() = hadithDatabseHelper.getAllBook()
}