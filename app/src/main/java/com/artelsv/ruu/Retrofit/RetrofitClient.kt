package com.artelsv.ruu.Retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {
    var retrofit: Retrofit? = null
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun getClient(url: String): Retrofit{
        if (retrofit == null){
            retrofit = Retrofit.Builder().client(okHttpClient).baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
        }

        return retrofit!!
    }
}