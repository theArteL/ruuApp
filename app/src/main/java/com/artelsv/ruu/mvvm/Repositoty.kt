package com.artelsv.ruu.mvvm

import android.media.Image
import com.artelsv.ruu.Model.FullNameModel
import com.artelsv.ruu.RetrofitTools.RetrofitHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repositoty(private val retrofitHelper: RetrofitHelper) {

    suspend fun getModelList() = retrofitHelper.getModelList()
    suspend fun postData(_action: RequestBody,_id: RequestBody, image: MultipartBody.Part,) = retrofitHelper.postData(_action, _id, image)
}