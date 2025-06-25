package com.example.szakdoga.videoPage.VideoModel

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class BrandingYoutube(

    @SerializedName("image")
    val image: ImageBanner

) {

    data class ImageBanner(
        @SerializedName("bannerExternalUrl")
        val bannerUrl: String
    )
}
