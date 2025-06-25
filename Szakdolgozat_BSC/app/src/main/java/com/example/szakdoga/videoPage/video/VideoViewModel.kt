package com.example.szakdoga.videoPage.video

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.szakdoga.videoPage.VideoModel.ChannelModel
import com.example.szakdoga.videoPage.VideoModel.VideoYoutubeModel
import com.example.szakdoga.videoPage.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoViewModel : ViewModel() {
    private val _video = MutableLiveData<VideoYoutubeModel?>()
    val video = _video
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _isAllVideosLoaded = MutableLiveData<Boolean>()
    val isAllVideosLoaded = _isAllVideosLoaded
    private val _message = MutableLiveData<String>()
    val message = _message
    var nextPageToken: String? = null
    var querySearch: String? = null

    init {

        getVideoList()
    }
     fun getVideoList(){
        _isLoading.value = true
        val client = ApiConfig
            .getService()
            .getVideo("snippet",
                "UClc7l3QYlJFaMygfDWGWmLw",
                "date",
                apikey = [API_KEY],
                nextPageToken,
                querySearch
            )
        client.enqueue(object : Callback<VideoYoutubeModel> {
            override fun onResponse(call: Call<VideoYoutubeModel>, response: Response<VideoYoutubeModel>) {
                _isLoading.value = false


                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null ){
                        Log.d(TAG, "Received data: $data")
                        if (data.nextPageToken != null){
                            nextPageToken = data.nextPageToken
                        }else{
                            _isAllVideosLoaded.value = true
                        }

                        if (data.items.isNotEmpty()){
                            _video.value = data
                        }


                    }else{
                        _message.value = "No channel"

                    }
                }else
                    _message.value = response.message()
            }

            override fun onFailure(call: Call<VideoYoutubeModel>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"Faild: ", t)
                _message.value = t.message
            }

        })
    }




    companion object{
        private val TAG = ChannelModel::class.java.simpleName
    }


}