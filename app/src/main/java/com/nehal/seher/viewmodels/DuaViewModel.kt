package com.nehal.seher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nehal.seher.repository.DuaRepository
import com.nehal.seher.utils.Resource
import kotlinx.coroutines.Dispatchers


class DuaViewModel(private val duaRepository : DuaRepository) :
    ViewModel() {
    fun getAllDuas() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = duaRepository.getAllDuas()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}