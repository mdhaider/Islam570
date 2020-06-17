package com.nehal.seher.room

class HadithDatabseHelper(private val hadithCollectionDao: HadithCollectionDao) {
    suspend fun getAllCollection() = hadithCollectionDao.getAllCollection()
    suspend fun getAllBook() = hadithCollectionDao.getAllBook()
}