package com.example.szakdoga.videoPage.VideoAdapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.szakdoga.databinding.OneVideoBinding
import com.example.szakdoga.videoPage.PlayerActivity
import com.example.szakdoga.videoPage.VideoDiffUtil.VideoDiffUtil
import com.example.szakdoga.videoPage.VideoModel.VideoYoutubeModel
import okhttp3.internal.notify

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

   private var oldItem = ArrayList<VideoYoutubeModel.VideoItem>()


    class  VideoHolder(itemView : OneVideoBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView


        fun setData(data: VideoYoutubeModel.VideoItem){


            binding.root.setOnClickListener{
                var id: VideoYoutubeModel.VideoId? = data.videoId
                val intent = Intent(it.context, PlayerActivity::class.java)
                intent.putExtra("videoId",id?.id)
                intent.putExtra("videoDescription",data.snippetYoutube.description)
                intent.putExtra("youtubeTitle",data.snippetYoutube.title)
                it.context.startActivity(intent)
            }


            binding.youtubeVideoTittle.text = data.snippetYoutube.title
            binding.dateOfPublishing.text = data.snippetYoutube.publishedAt
            Glide.with(binding.root).load(data.snippetYoutube.thumbnails.high.url).into(binding.videoThumbnail)
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = OneVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(view)
    }

    override fun getItemCount(): Int {

        return oldItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).setData(oldItem[position])
    }

    fun setData(newList: List<VideoYoutubeModel.VideoItem>, recyclerView: RecyclerView){
        val videoDiff = VideoDiffUtil(oldItem, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItem.addAll(newList)
        diff.dispatchUpdatesTo(this)
        recyclerView.scrollToPosition(oldItem.size - newList.size)
    }

    fun crealAll(){
        oldItem.clear()
        notifyDataSetChanged()
    }

}