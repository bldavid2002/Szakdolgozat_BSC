package com.example.szakdoga.videoPage.VideoModel

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class VideoYoutubeModel(


    @SerializedName("nextPageToken")
    val nextPageToken: String?,

    @SerializedName("items")
    val items: List<VideoItem>,

){



    data class VideoItem(
        @SerializedName("id")
        val videoId: VideoId?,



        @SerializedName("snippet")
        val snippetYoutube: SnippetYoutube
    )

    data class VideoId(
        @SerializedName("videoId")
        val id: String?
    )



}
