package com.nehal.seher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nehal.seher.room.HadithCollectionRepository
import com.nehal.seher.utils.Resource
import kotlinx.coroutines.Dispatchers


class HadithListViewModel(private val hadithCollectionRepository: HadithCollectionRepository) :
    ViewModel() {
    fun getAllCollection() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = hadithCollectionRepository.getAllCollection()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun getAllBook() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = hadithCollectionRepository.getALlBook()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}