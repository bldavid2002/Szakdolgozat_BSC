package com.example.szakdoga.videoPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.example.szakdoga.databinding.ActivityPlaylistVideoPlayerBinding
import com.example.szakdoga.videoPage.VideoAdapter.PlaylistItemAdapter
import com.example.szakdoga.videoPage.viewModel.OnVideoDataRecivedCallback
import com.example.szakdoga.videoPage.viewModel.PlaylistVideoViewModel
import java.lang.StringBuilder

class PlaylistVideoPlayerActivity : AppCompatActivity(),OnVideoDataRecivedCallback {

    private var _binding: ActivityPlaylistVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private var playlistVideoViewModel: PlaylistVideoViewModel? = null
    private lateinit var webView: WebView
    private lateinit var video: String
    private  var playlistId :String? = null
    private var videoId: String? = null
    private var videoTitle: String? = null
    private val adapter = PlaylistItemAdapter()
    private var isLoading = false
    private var isScroll = false
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    private var isAllVideoLoaded = false
    private var isPlayingVideo = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlaylistVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        playlistVideoViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())
            .get(PlaylistVideoViewModel::class.java)

        playlistVideoViewModel?.playlistItem?.observe(this) { playlistItem ->


            val stringBuilder = StringBuilder()


            val manager = LinearLayoutManager(this, HORIZONTAL, false)
            binding.playlistPlayerRecycleView.adapter = adapter
            binding.playlistPlayerRecycleView.layoutManager = manager

            binding.playlistPlayerRecycleView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        isScroll = true
                    }
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    currentItem = manager.childCount
                    totalItem = manager.itemCount
                    scrollOutItem = manager.findFirstVisibleItemPosition()
                    if (isScroll && (currentItem + scrollOutItem == totalItem)) {
                        isScroll = false

                        if (!isLoading) {
                            if (!isAllVideoLoaded) {
                                val playlistId = intent.getStringExtra("playlist_id")
                                playlistId?.let { playlistVideoViewModel?.getPlaylistVideo(it) }

                            } else {
                                Toast.makeText(
                                    this@PlaylistVideoPlayerActivity,
                                    "All video loaded",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                }

            })






            adapter.addListener = PlaylistItemAdapter.ItemClickedListener { data ->
                data.contentDetail.videoId?.let { id ->

                    onVideoIdRecived(id)

                }

                data.snippetYoutube.title?.let { title ->
                    onVideoTitleRecived(title)

                }

                data.snippetYoutube.description?.let { description ->
                    onVideoDescriptionRecived(description)

                }
            }

            val firstVideoId = playlistItem?.items?.get(0)?.contentDetail?.videoId
            firstVideoId.let {


            }


        }

        playlistVideoViewModel?.playlistItem?.observe(this) {
            adapter.setData(it!!.items, binding.playlistPlayerRecycleView)
            if (!isPlayingVideo) {
                isPlayingVideo = true
                it?.items?.get(0)?.contentDetail?.videoId?.let { it1 ->
                    onVideoIdRecived(it1)

                }
                onVideoIdRecived(it?.items?.get(0)?.contentDetail?.videoId)

                onVideoTitleRecived(it?.items?.get(0)?.snippetYoutube?.title)

                onVideoDescriptionRecived(it?.items?.get(0)?.snippetYoutube?.description)

                binding.playlistOneDescription.movementMethod = ScrollingMovementMethod()

            }

        }

        playlistVideoViewModel?.isLoading?.observe(this) {
            isLoading = it
            binding.progressBarVideoLoading.visibility =
                if (isLoading)
                    View.VISIBLE
                else
                    View.GONE
        }

        playlistVideoViewModel?.isAllItemLoaded?.observe(this) {
            isAllVideoLoaded = it
            if (it) Toast.makeText(this, "All video has been loaded", Toast.LENGTH_SHORT).show()
        }



        playlistId = intent.getStringExtra("playlist_id")
        playlistId?.let {
            playlistVideoViewModel?.getPlaylistVideo(it)


        }




    }

    override fun onVideoIdRecived(videoId: String?) {
        webView = binding.webView

        video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        webView.loadData(video, "text/html", "utf-8")
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()

    }

    override fun onVideoDescriptionRecived(description: String?) {
        binding.playlistOneDescription.text = description

    }

    override fun onVideoTitleRecived(title: String?) {
        binding.playlistPlayerTitle.text = title

    }
}