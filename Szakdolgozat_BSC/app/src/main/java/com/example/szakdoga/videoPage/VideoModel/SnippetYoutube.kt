package com.example.szakdoga.videoPage.VideoModel

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SnippetYoutube (

    @SerializedName("title")
    val title:String,

    @SerializedName("description")
    val description:String,

    @SerializedName("customUrl")
    val customUrl:String,

    @SerializedName("publishedAt")
    val publishedAt:String,


    @SerializedName("thumbnails")
    val thumbnails:ThumbnailsYoutube,

    @SerializedName("position")
    val country: Int?,

    @SerializedName("embedHtml")
    val embedhtml: String?

)