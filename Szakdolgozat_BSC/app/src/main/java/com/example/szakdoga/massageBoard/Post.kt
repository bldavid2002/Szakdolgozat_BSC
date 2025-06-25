package com.example.szakdoga.massageBoard

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract.DisplayPhoto
import com.google.android.material.textfield.TextInputLayout.LengthCounter


data class Post(
    var title: String = "",
    var description: String = "",
    var picture: String = "",
    var userId: String = "",
    val userPhoto: String = "",
    var timeStamp: Any? = null,
    var postKey: String? = "",
    var likeCounter: Int = 0,
    var dislikeCounter: Int = 0,
    var likedBy: MutableSet<String> = mutableSetOf()

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readValue(Any::class.java.classLoader),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(picture)
        parcel.writeString(userId)
        parcel.writeString(userPhoto)
        parcel.writeValue(timeStamp)
        parcel.writeString(postKey)
        parcel.writeInt(likeCounter)
        parcel.writeInt(dislikeCounter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }

    fun likePost(userId: String){
        if(!likedBy.contains(userId)){
            likedBy.add(userId)
            likeCounter++
        }
    }

}