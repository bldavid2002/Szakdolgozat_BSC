package com.example.szakdoga.videoPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.example.szakdoga.R
import com.example.szakdoga.databinding.ActivityVidoPlayerBinding
import okhttp3.internal.wait

class PlayerActivity : AppCompatActivity() {

    private var _binding: ActivityVidoPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var webView: WebView
    private lateinit var video: String
    private var videoId: String? = null
    private var videoDescrtiption: String? = null
    private var videoTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVidoPlayerBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)


        webView = binding.webView

        videoId = intent.getStringExtra("videoId")
        videoDescrtiption = intent.getStringExtra("videoDescription")
        videoTitle = intent.getStringExtra("youtubeTitle")

        video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"

        webView.loadData(video, "text/html", "utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()

        binding.vidoPlayerTitle.text = videoTitle
        binding.vidoPlayerDescription.text = videoDescrtiption


    }




}