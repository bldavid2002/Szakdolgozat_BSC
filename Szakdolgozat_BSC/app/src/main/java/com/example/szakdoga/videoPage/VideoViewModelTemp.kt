package com.example.szakdoga.videoPage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.szakdoga.videoPage.VideoModel.ChannelModel
import com.example.szakdoga.videoPage.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoViewModelTemp : ViewModel() {

    private val _channel = MutableLiveData<ChannelModel?>()
    val channel = _channel
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _message = MutableLiveData<String>()
    val message = _message

    init {
        getChannel()
    }


    private fun getChannel(){
        _isLoading.value = true
        val client = ApiConfig.getService().getChannel("snippet,brandingSettings","UClc7l3QYlJFaMygfDWGWmLw", apikey = [API_KEY])
        client.enqueue(object : Callback<ChannelModel> {
            override fun onResponse(call: Call<ChannelModel>, response: Response<ChannelModel>) {
                _isLoading.value = false


                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null && data.items.isNotEmpty()){
                        _channel.value = data

                    }else{
                        _message.value = "No channel"

                    }
                }else
                    _message.value = response.message()
            }

            override fun onFailure(call: Call<ChannelModel>, t: Throwable) {
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