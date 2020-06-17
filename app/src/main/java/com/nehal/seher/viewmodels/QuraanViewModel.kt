package com.nehal.seher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nehal.seher.repository.QuraanRepository
import com.nehal.seher.utils.Resource
import kotlinx.coroutines.Dispatchers


class QuraanViewModel(private val quraanRepository: QuraanRepository) : ViewModel() {

    fun getAllSurah() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
           emit(Resource.success(data = quraanRepository.getAllSurah()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getParas() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = quraanRepository.getAllParas()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getHadis() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = quraanRepository.getAllHadis()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}