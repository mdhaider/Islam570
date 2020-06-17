package com.nehal.seher.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nehal.seher.repository.HomeRepository
import com.nehal.seher.utils.Resource
import kotlinx.coroutines.Dispatchers


class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    fun getAllNames(fileName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
           emit(Resource.success(data = homeRepository.getNames(fileName)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAllDuas(fileName: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = homeRepository.getDuas(fileName)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getAllUrduPosters() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = homeRepository.getUrduPosters()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getHijriDateFromGregDate(date:String, adjustment:Int?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = homeRepository.getHijriFromGreg(date, adjustment)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getPrayerTimes(
        dateOrTimestamp:String,
        latitude: String,
        longitude: String,
        method: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = homeRepository.getPrayerTimes(dateOrTimestamp,
                        latitude,
                        longitude,
                        method
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}