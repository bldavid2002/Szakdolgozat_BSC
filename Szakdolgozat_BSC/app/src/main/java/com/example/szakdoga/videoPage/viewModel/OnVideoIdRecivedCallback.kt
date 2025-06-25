package com.example.szakdoga.videoPage.viewModel

interface OnVideoDataRecivedCallback {

    fun onVideoIdRecived(videoId: String?)

    fun onVideoDescriptionRecived(description: String?)

    fun onVideoTitleRecived(tile: String?)
}