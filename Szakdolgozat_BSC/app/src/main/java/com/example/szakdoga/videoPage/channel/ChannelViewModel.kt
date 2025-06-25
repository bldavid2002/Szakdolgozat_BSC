package com.example.szakdoga.videoPage.channel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.szakdoga.videoPage.VideoModel.ChannelModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ChannelViewModel: ViewModel() {


    private val _channel = MutableLiveData<ChannelModel?>()
    val channel = _channel
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading
    private val _message = MutableLiveData<String>()
    val message = _message

    init {
        getChannel()
    }

    private fun getChannel() {
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://www.googleapis.com/youtube/v3/channels?part=snippet,brandingSettings&id=UClc7l3QYlJFaMygfDWGWmLw&key=[API_KEY]")
                val connection = url.openConnection() as HttpURLConnection
                Log.d("YOUTUBE", "Response Code: ${connection.responseCode}")
                connection.requestMethod = "GET"

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line).append("\n")
                    }

                    Log.d("YOUTUBE", "Response: $response")

                    val gson = Gson()
                    val channelModel = gson.fromJson(response.toString(), ChannelModel::class.java)
                    _channel.postValue(channelModel)
                    _isLoading.postValue(false)
                } else {
                    _isLoading.postValue(false)
                    _message.postValue("Failed to fetch data. Response code: ${connection.responseCode}")
                }
            } catch (e: Exception) {
                Log.e("YOUTUBE", "Exception: ${e.message}", e)
                _isLoading.postValue(false)
                _message.postValue("Error: ${e.message}")
            }
        }
    }



    companion object val TAG = ChannelViewModel::class.java.simpleName

}