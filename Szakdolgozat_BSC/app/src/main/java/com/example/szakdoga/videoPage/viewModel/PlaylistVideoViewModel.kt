package com.example.szakdoga.videoPage.viewModel

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.szakdoga.videoPage.VideoModel.PlaylistVideoModel
import com.example.szakdoga.videoPage.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistVideoViewModel: ViewModel() {

    private val _playlistItem = MutableLiveData<PlaylistVideoModel?>()
    val playlistItem = _playlistItem
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _isAllItemLoaded = MutableLiveData<Boolean>()
    val isAllItemLoaded = _isAllItemLoaded
    var nextPageToken: String? = null



     fun getPlaylistVideo(playlistId: String) {
        _isLoading.value = true
         val apiUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=contentDetails,snippet&playlistId=$playlistId&key=[API_KEY]"


         val client = ApiConfig
            .getService().getPlaylistVideo("snippet,contentDetails",playlistId, nextPageToken)
         client.enqueue(object :Callback<PlaylistVideoModel>{
            override fun onResponse(
                call: Call<PlaylistVideoModel>,
                response: Response<PlaylistVideoModel>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val data = response.body()

                    if (data != null) {
                        if (data.nextPageToken != null) {
                            nextPageToken = data.nextPageToken
                        } else {
                            nextPageToken = null
                            _isAllItemLoaded.value = true
                        }
                        if (data.items.isNotEmpty()) {
                            _playlistItem.value = data
                        }
                    }
                }
            }





            override fun onFailure(call: Call<PlaylistVideoModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure",t)
            }
        })
    }
    companion object{
        private val TAG = PlaylistVideoModel::class.java.simpleName
    }
}