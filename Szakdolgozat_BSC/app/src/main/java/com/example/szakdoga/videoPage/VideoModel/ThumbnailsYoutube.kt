package com.example.szakdoga.videoPage.VideoModel

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class ThumbnailsYoutube(

    @SerializedName("default")
    val default: ThumbnailDetail,

    @SerializedName("medium")
    val medium: ThumbnailDetail,

    @SerializedName("high")
    val high: ThumbnailDetail
)

@Keep
data class ThumbnailDetail(

    @SerializedName("url")
    val url: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int
)
