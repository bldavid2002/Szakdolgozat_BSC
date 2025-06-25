package com.example.szakdoga.videoPage.VideoModel

import android.provider.ContactsContract.SearchSnippets
import android.support.annotation.Keep
import com.example.szakdoga.databinding.FragmentChannelBinding
import com.google.gson.annotations.SerializedName


@Keep
 data class ChannelModel (
     @SerializedName("items")
     val items: List<Items>
 ) {

    data class Items(


        @SerializedName("id")
        val id: String,

        @SerializedName("snippet")
        val snippets: SnippetYoutube?,

        @SerializedName("brandingSettings")
        val branding: BrandingYoutube
    )

}

