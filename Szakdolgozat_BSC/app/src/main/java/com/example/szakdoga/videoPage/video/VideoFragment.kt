package com.example.szakdoga.videoPage.video

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.szakdoga.R
import com.example.szakdoga.databinding.FragmentVideoBinding
import com.example.szakdoga.videoPage.VideoAdapter.VideoAdapter
import com.example.szakdoga.videoPage.VideoViewModelTemp
import java.lang.StringBuilder
import kotlin.concurrent.fixedRateTimer


class VideoFragment : Fragment() {


    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private  var videoViewModel: VideoViewModel? = null
    private val adapter = VideoAdapter()
    private var isLoading = false
    private var isScroll = false
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    private var isAllVideoLoaded = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(requireContext())
        binding.recycleViewVideo.adapter = adapter
        binding.recycleViewVideo.layoutManager = manager

        binding.recycleViewVideo.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItem = manager.childCount
                totalItem = manager.itemCount
                scrollOutItem = manager.findFirstVisibleItemPosition()
                if (isScroll && (currentItem + scrollOutItem == totalItem)){
                    isScroll = false

                    if(!isLoading){
                        if (!isAllVideoLoaded){
                            videoViewModel?.getVideoList()

                        }else{
                            Toast.makeText(requireContext(), "All video loaded", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        })

        videoViewModel?.video?.observe(viewLifecycleOwner,{
            if (it != null && it.items.isNotEmpty()){
                adapter.setData(it.items, binding.recycleViewVideo)
            }
        })

        videoViewModel?.isLoading?.observe(viewLifecycleOwner, {
            isLoading = it
            binding.progressBarVideoLoading.visibility =
                if(isLoading)
                    View.VISIBLE
                else
                    View.GONE
        })

        videoViewModel?.isAllVideosLoaded?.observe(viewLifecycleOwner, {
            isAllVideoLoaded = it
            if (it) Toast.makeText(requireContext(), "All video has been loaded", Toast.LENGTH_SHORT).show()
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        videoViewModel =
            ViewModelProvider(this).get(VideoViewModel::class.java)
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.video_search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search_video)
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {

                if (p0.isNotEmpty()){
                    videoViewModel?.querySearch = p0
                    videoViewModel?.nextPageToken = null
                    adapter.crealAll()
                    videoViewModel?.getVideoList()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if(newText.isNotEmpty()){
                    videoViewModel?.querySearch = null
                    videoViewModel?.nextPageToken = null
                    adapter.crealAll()
                    videoViewModel?.getVideoList()
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }




}