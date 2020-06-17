package com.nehal.seher.room.dbhelper

import com.nehal.seher.room.dao.HadithCollectionDao

class HadithDatabseHelper(private val hadithCollectionDao: HadithCollectionDao) {
    suspend fun getAllCollection() = hadithCollectionDao.getAllCollection()
    suspend fun getAllBook() = hadithCollectionDao.getAllBook()
}