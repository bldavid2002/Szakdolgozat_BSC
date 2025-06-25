package com.example.szakdoga.videoPage.network

import com.example.szakdoga.videoPage.VideoModel.ChannelModel
import com.example.szakdoga.videoPage.VideoModel.PlaylistVideoModel
import com.example.szakdoga.videoPage.VideoModel.PlaylistYoutubeModel
import com.example.szakdoga.videoPage.VideoModel.VideoYoutubeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("channels")
    fun getChannel(
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("key") apikey: String
    ) : Call<ChannelModel>


    @GET("search")
    fun getVideo(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("order") order: String,
        @Query("key") apikey: String,
        @Query("pageToken") pageToken: String?,
        @Query("q") query: String?
    ) : Call<VideoYoutubeModel>

    @GET("playlists")
    fun getPlaylist(
        @Query("part")  part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: String,
        @Query("pageToken") pageToken: String?
    ) : Call<PlaylistYoutubeModel>


    @GET("playlistItems")
    fun getPlaylistVideo(
        @Query("part") part: String,
        @Query("playlistId") playlistId: String,
        @Query("pageToken") pageToken: String?
    ) : Call<PlaylistVideoModel>

}