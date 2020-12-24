package com.artelsv.ruu.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.artelsv.ruu.Utils.Resource
import kotlinx.coroutines.Dispatchers

class GetModelViewModel(private val repositoty: Repositoty) : ViewModel() {

    fun getModelList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repositoty.getModelList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}