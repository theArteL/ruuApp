package com.artelsv.ruu.Model

import com.google.gson.annotations.SerializedName

data class GetModelList(
    @SerializedName("data")
    var list: List<GetModel>
)