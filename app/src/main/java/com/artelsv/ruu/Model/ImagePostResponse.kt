package com.artelsv.ruu.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImagePostResponse(@SerializedName("status")
                             @Expose
                             var status: String = "")