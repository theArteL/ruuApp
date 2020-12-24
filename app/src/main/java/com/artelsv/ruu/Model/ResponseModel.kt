package com.artelsv.ruu.Model

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("status")
    var status: String = "",
    @SerializedName("msg")
    var msg: String = ""
)