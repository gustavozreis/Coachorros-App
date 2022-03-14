package com.gustavozreis.dogvacionalapp.network

import com.squareup.moshi.Json

data class DogPhotoModel (
    @Json(name = "message")val imgUrl: String,
    val status: String
        )