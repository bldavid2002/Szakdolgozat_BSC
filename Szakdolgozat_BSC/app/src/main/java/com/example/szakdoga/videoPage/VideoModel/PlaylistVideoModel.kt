package com.example.szakdoga.videoPage.VideoModel

import com.google.gson.annotations.SerializedName

data class PlaylistVideoModel(

    @SerializedName("nextPageToken")
    val nextPageToken: String?,


    @SerializedName("items")
    val items: List<PlaylistYoutubeModel.PlaylistItem>



)
