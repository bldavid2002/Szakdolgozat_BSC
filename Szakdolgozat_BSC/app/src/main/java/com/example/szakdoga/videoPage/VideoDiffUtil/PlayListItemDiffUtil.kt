package com.example.szakdoga.videoPage.VideoDiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.szakdoga.videoPage.VideoModel.PlaylistYoutubeModel
import com.example.szakdoga.videoPage.VideoModel.VideoYoutubeModel

class PlayListItemDiffUtil (
    private val oldList: List<PlaylistYoutubeModel.PlaylistItem>,
    private val newList: List<PlaylistYoutubeModel.PlaylistItem>
) :DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldVideo = oldList[oldItemPosition]
        val newVideo = newList[newItemPosition]

        return oldVideo.snippetYoutube.title == newVideo.snippetYoutube.title
    }

}