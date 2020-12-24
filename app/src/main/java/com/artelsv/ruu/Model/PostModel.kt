package com.artelsv.ruu.Model

import android.media.Image

data class PostModel(val id: Int? = null,
                     var image: Image? = null,
                     var contact: FullNameModel? = null)