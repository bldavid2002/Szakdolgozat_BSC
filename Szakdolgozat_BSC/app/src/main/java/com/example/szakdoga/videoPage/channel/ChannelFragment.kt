package com.example.szakdoga.videoPage.channel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.szakdoga.R
import com.example.szakdoga.databinding.FragmentChannelBinding

class ChannelFragment : Fragment() {


    private var _binding: FragmentChannelBinding? = null
    private val binding get() = _binding!!

    private  var  channelViewModel: ChannelViewModel? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        channelViewModel?.channel?.observe(viewLifecycleOwner,{
            if (it != null && it.items.isNotEmpty()){
                it.items.forEach {  channel ->
                    Log.d("YOUTUBE" ,"Channel title: ${channel.snippets?.title}")
                    binding.channelName.text = channel.snippets?.title
                    binding.channelDescription.text =channel.snippets?.description
                    Glide.with(requireContext()).load(channel.branding.image.bannerUrl).into(binding.imageBrand)
                    Glide.with(requireContext()).load(channel.snippets?.thumbnails?.high?.url).into(binding.imgLogo)
                }
            }
        })

        channelViewModel?.isLoading?.observe(viewLifecycleOwner,{
            if (it){
                binding.progressBar.visibility = View.VISIBLE

            }else{
                binding.progressBar.visibility = View.GONE
            }
        })
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        channelViewModel = ViewModelProvider(this).get(ChannelViewModel::class.java)
        _binding = FragmentChannelBinding.inflate(inflater, container,false)
        return binding.root
    }



}


