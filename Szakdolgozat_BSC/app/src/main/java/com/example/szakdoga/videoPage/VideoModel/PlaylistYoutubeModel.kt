package com.example.szakdoga.videoPage.VideoModel

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class PlaylistYoutubeModel(

    @SerializedName("nextPageToken")
    val nextPageToken: String,


    @SerializedName("items")
    val items: List<PlaylistItem>
){

    data class PlaylistItem(
        @SerializedName("id")
        val id: String,

        @SerializedName("snippet")
        val snippetYoutube: SnippetYoutube,

        @SerializedName("contentDetails")
        val contentDetail: ContentDetail
    )

    data class ContentDetail(
        @SerializedName("itemCount")
        val itemCount: Int,

        @SerializedName("videoId")
        val videoId: String,

        @SerializedName("videoPublishedAt")
        val videoPublishedAt: String
    )
}
