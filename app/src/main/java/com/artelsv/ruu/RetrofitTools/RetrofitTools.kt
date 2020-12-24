package com.artelsv.ruu.RetrofitTools

import android.app.AlertDialog
import android.util.Log
import com.artelsv.ruu.Interface.RetrofitServices
import com.artelsv.ruu.Model.GetModel
import com.artelsv.ruu.Model.GetModelList
import com.artelsv.ruu.Retrofit.RetrofitClient
import com.artelsv.ruu.Retrofit.RetrofitUnsafeClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RetrofitTools {
    private val BASE_URL = "https://test-job.pixli.app/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
    val unsafeRetrofitService: RetrofitServices
        get() = RetrofitUnsafeClient.getRetrofit(BASE_URL).create(RetrofitServices::class.java)
}