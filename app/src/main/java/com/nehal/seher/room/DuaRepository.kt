package com.nehal.seher.room

class DuaRepository(private val duaDatabseHelper: DuaDatabseHelper) {
    suspend fun getAllDuas() = duaDatabseHelper.getAllDuas()
}