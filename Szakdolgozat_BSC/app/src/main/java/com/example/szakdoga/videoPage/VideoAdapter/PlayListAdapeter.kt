package com.example.szakdoga.videoPage.VideoAdapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.szakdoga.databinding.OnePlaylistItemBinding
import com.example.szakdoga.videoPage.PlaylistVideoPlayerActivity
import com.example.szakdoga.videoPage.VideoDiffUtil.PlayListDiffUtil
import com.example.szakdoga.videoPage.VideoModel.PlaylistYoutubeModel

class PlayListAdapeter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldItems = ArrayList<PlaylistYoutubeModel.PlaylistItem>()



    class PlaylistHolder(itemView: OnePlaylistItemBinding): RecyclerView.ViewHolder(itemView.root){

        private val binding = itemView

        fun setData(data: PlaylistYoutubeModel.PlaylistItem){
            binding.root.setOnClickListener{
                val intent = Intent(it.context, PlaylistVideoPlayerActivity::class.java)
                val id  = data.id
                val videoID = data.contentDetail.videoId
                intent.putExtra("playlist_id", data.id)
                intent.putExtra("videoId", data.contentDetail.videoId)
                intent.putExtra("videoDescription",data.snippetYoutube.description)
                intent.putExtra("youtubeTitle",data.snippetYoutube.title)
                it.context.startActivity(intent)
            }
            binding.playlistTitle.text = data.snippetYoutube.title
            val videoCount = "${data.contentDetail.itemCount} videos"
            binding.videoCount.text = videoCount
            Glide.with(binding.root).load(data.snippetYoutube.thumbnails.high.url)
                .into(binding.imageView2)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = OnePlaylistItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PlaylistHolder(view)
    }

    override fun getItemCount(): Int {
        return oldItems.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaylistHolder).setData(oldItems[position])
    }


    fun setData(newList: List<PlaylistYoutubeModel.PlaylistItem>, recyclerView: RecyclerView){
        val playlistDiff = PlayListDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(playlistDiff)
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
        recyclerView.scrollToPosition(oldItems.size - newList.size)
    }

    fun crealAll(){
        oldItems.clear()
        notifyDataSetChanged()
    }
}