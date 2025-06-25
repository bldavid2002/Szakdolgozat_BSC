package com.example.szakdoga.videoPage.VideoAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.szakdoga.databinding.ItemPlaylistItemBinding
import com.example.szakdoga.databinding.OneVideoBinding
import com.example.szakdoga.videoPage.VideoDiffUtil.PlayListItemDiffUtil
import com.example.szakdoga.videoPage.VideoModel.PlaylistYoutubeModel


class PlaylistItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldItems = ArrayList<PlaylistYoutubeModel.PlaylistItem>()
    var currentSelected: Int? = 0
    var addListener: ItemClickedListener? = null


    inner class PlaylistItemHolder(itemView: ItemPlaylistItemBinding):
            RecyclerView.ViewHolder(itemView.root){
                private val binding = itemView

                fun setData(data: PlaylistYoutubeModel.PlaylistItem, selected: Boolean, function: (Int) -> Unit, position: Int){

                    binding.root.isSelected = selected
                    binding.root.setOnClickListener{
                        function(position)
                        if (!selected){
                            addListener?.onClick(data)
                        }
                    }

                    binding.youtubeVideoTittle.text = data.snippetYoutube.title
                    binding.dateOfPublishing.text = data.snippetYoutube.publishedAt
                    Glide.with(binding.root)
                        .load(data.snippetYoutube.thumbnails.high.url)
                        .into(binding.videoThumbnail)

                }



            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemPlaylistItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return PlaylistItemHolder(view)
    }

    override fun getItemCount(): Int {
        return oldItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val fundction ={pos : Int ->
            if (currentSelected == null || currentSelected != pos) {
                currentSelected = pos
                notifyDataSetChanged()
            }
        }

        (holder as PlaylistItemHolder).setData(oldItems[position], position == currentSelected, fundction, position)
    }


    fun setData(newList: List<PlaylistYoutubeModel.PlaylistItem>, recyclerView: RecyclerView){
        val videoDiff = PlayListItemDiffUtil(oldItems, newList)
        val diff = DiffUtil.calculateDiff(videoDiff)
        oldItems.addAll(newList)
        diff.dispatchUpdatesTo(this)
        recyclerView.scrollToPosition(oldItems.size - newList.size)
    }
    fun interface ItemClickedListener{
        fun onClick(data: PlaylistYoutubeModel.PlaylistItem)
    }
}