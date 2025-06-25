package com.example.szakdoga.videoPage.playList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.szakdoga.videoPage.VideoModel.PlaylistYoutubeModel
import com.example.szakdoga.videoPage.VideoModel.VideoYoutubeModel
import com.example.szakdoga.videoPage.network.ApiConfig
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class PlayListViewModel: ViewModel() {

    private val _playlist = MutableLiveData<PlaylistYoutubeModel>()
    val playlist = _playlist
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _isAllPlayListLoading = MutableLiveData<Boolean>()
    val isAllPlayListLoading = _isAllPlayListLoading

    var nextPageToken: String? = null


    init {
        getPlaylist()
    }

     fun getPlaylist(){
        _isLoading.value = true
        val client = ApiConfig
            .getService()
            .getPlaylist("snippet,contentDetails",
                "[ID]",
                "10",
                nextPageToken)

        client.enqueue(object : retrofit2.Callback<PlaylistYoutubeModel>{
            override fun onResponse(
                call: Call<PlaylistYoutubeModel>,
                response: Response<PlaylistYoutubeModel>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    val data = response.body()
                    if(data != null){
                        if (data.nextPageToken != null){
                            nextPageToken = data.nextPageToken
                        }else{
                            _isAllPlayListLoading.value = true
                        }
                        if (data.items.isNotEmpty()){
                            _playlist.value = data
                        }
                    }
                }

            }

            override fun onFailure(call: Call<PlaylistYoutubeModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(Tag,"Failour",t)
            }

        })
    }

    companion object{
        private val Tag = PlaylistYoutubeModel::class.java.simpleName
    }

}