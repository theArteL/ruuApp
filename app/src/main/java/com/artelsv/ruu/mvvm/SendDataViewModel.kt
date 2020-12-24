package com.artelsv.ruu.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.artelsv.ruu.Utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SendDataViewModel(private val repositoty: Repositoty) : ViewModel() {

    fun postData(_action: RequestBody,_id: RequestBody, image: MultipartBody.Part) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repositoty.postData(_action ,_id, image)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}