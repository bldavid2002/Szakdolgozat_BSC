package com.example.szakdoga.massageBoard


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.szakdoga.R

class MessageBoardAdapter(private val mContext: Context ,private val mData: List<Post>,private var postClickListener: PostClickListener, private var lIkeDislikeListener: LIkeDislikeListener) : RecyclerView.Adapter<MessageBoardAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d("Navigation", "onCreateViewHolder called")
        val row = LayoutInflater.from(mContext).inflate(R.layout.post_one_item, parent, false)
        return MyViewHolder(row)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = mData[position].title
        Glide.with(mContext).load(mData[position].picture).into(holder.imgPost)
        Glide.with(mContext).load(mData[position].userPhoto).into(holder.imgPostProfile)


    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle = itemView.findViewById<TextView>(R.id.row_title)
        val imgPost = itemView.findViewById<ImageView>(R.id.row_img)
        val imgPostProfile = itemView.findViewById<ImageView>(R.id.row_profp_img)
        val likeButton = itemView.findViewById<ImageView>(R.id.LikeButton)
        val dislikeButton = itemView.findViewById<ImageView>(R.id.DisLikeButton)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    postClickListener?.onPostClick(mData[position])
                }
            }

            likeButton.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    Log.d("Likes","Like post at position:  $position")
                    mData[position].likeCounter++


                    val post =mData[position]
                    val currentUserId =post.userId


                    if (post.likedBy.contains(currentUserId)){
                        post.likePost(currentUserId)
                        lIkeDislikeListener.onLikeDislikeClicked(post)
                    }else{
                        Toast.makeText(mContext, "Ezt a bejegyzést már lájkoltad",Toast.LENGTH_SHORT).show()
                    }

                }
            }

            dislikeButton.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    Log.d("Likes","Dislike post at position:  $position")
                    mData[position].dislikeCounter++
                    lIkeDislikeListener.onLikeDislikeClicked(mData[position])
                }
            }


        }
    }
}







