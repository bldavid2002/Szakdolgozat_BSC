package com.example.szakdoga.videoPage.VideoDiffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.szakdoga.videoPage.VideoModel.VideoYoutubeModel

class VideoDiffUtil (
    private val oldList: List<VideoYoutubeModel.VideoItem>,
    private val newList: List<VideoYoutubeModel.VideoItem>
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