package com.artelsv.ruu.Interface

import android.graphics.Bitmap
import android.media.Image
import com.artelsv.ruu.Model.FullNameModel
import com.artelsv.ruu.Model.GetModelList
import com.artelsv.ruu.Model.ImagePostResponse
import com.artelsv.ruu.Model.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("get.php")
    suspend fun getModelList(@Query("action") action: String = "get_bodyParts"): GetModelList

    @Multipart
    @POST("send.php")
    suspend fun postData(@Part("action") action: RequestBody,
                          @Part("id") id: RequestBody,
                          @Part image: MultipartBody.Part): ImagePostResponse

    @Multipart
    @POST("send.php")
    fun postData2(@Part("action") action: RequestBody,
                  @Part("id") id: RequestBody,
                  @Part image: MultipartBody.Part): Call<ImagePostResponse>

//    @Multipart
//    @POST("send.php")
//    fun postData2(@Query("action") action: String,
//                  @Query("id") id: Int,
//                  @Part image: MultipartBody.Part): Call<ResponseModel>
}