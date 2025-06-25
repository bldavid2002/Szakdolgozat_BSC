package com.example.szakdoga.videoPage.playList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.szakdoga.R
import com.example.szakdoga.databinding.FragmentPlaylistBinding
import com.example.szakdoga.databinding.FragmentVideoBinding
import com.example.szakdoga.videoPage.VideoAdapter.PlayListAdapeter

class PlayListFragment : Fragment() {

    private var _binding: FragmentPlaylistBinding? =null
    private val binding get() = _binding!!
    private  var  playListViewModel: PlayListViewModel? = null
    private val adapter = PlayListAdapeter()
    private var isLoading = false
    private var isScroll = false
    private var currentItem = -1
    private var totalItem = -1
    private var scrollOutItem = -1
    private var isAllVideoLoaded = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = LinearLayoutManager(requireContext())

        binding.playlistRecycleView.adapter = adapter
        binding.playlistRecycleView.layoutManager = manager

        binding.playlistRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
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
                            playListViewModel?.getPlaylist()

                        }else{
                            Toast.makeText(requireContext(), "All video loaded", Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }

        })




        playListViewModel?.playlist?.observe(viewLifecycleOwner, {
            adapter.setData(it?.items!!, binding.playlistRecycleView)
        })

        playListViewModel?.isLoading?.observe(viewLifecycleOwner,{
            isLoading = it
            binding.progressBarVideoLoading.visibility =
                if(isLoading)
                    View.VISIBLE
                else
                    View.GONE
        })

        playListViewModel?.isAllPlayListLoading?.observe(viewLifecycleOwner, {
            isAllVideoLoaded = it
            if (it) Toast.makeText(requireContext(), "All video has been loaded", Toast.LENGTH_SHORT).show()
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        playListViewModel =
            ViewModelProvider(this).get(PlayListViewModel::class.java)
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

}