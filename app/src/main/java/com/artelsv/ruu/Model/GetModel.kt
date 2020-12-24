package com.artelsv.ruu.Model

import com.google.gson.annotations.SerializedName

data class GetModel(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("target")
    var target: String = ""
)