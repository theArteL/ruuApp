package com.artelsv.ruu.RetrofitTools

import android.media.Image
import com.artelsv.ruu.Interface.RetrofitServices
import com.artelsv.ruu.Model.FullNameModel
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RetrofitHelper(private val retrofitServices: RetrofitServices) {
    suspend fun getModelList() = retrofitServices.getModelList()
    suspend fun postData(_action: RequestBody,_id: RequestBody, image: MultipartBody.Part,) = retrofitServices.postData(_action, _id, image)
}