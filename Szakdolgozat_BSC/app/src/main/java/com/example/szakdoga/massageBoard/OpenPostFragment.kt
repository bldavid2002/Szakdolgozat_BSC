package com.example.szakdoga.massageBoard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.szakdoga.R


class OpenPostFragment : Fragment() {
    private lateinit var selectedPost: Post

    companion object {
        fun newInstance(post: Post): OpenPostFragment {
            val fragment = OpenPostFragment()
            val args = Bundle()
            args.putParcelable("selected_post", post)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_detail_opend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            selectedPost = it.getParcelable("selected_post")!!
            displaySelectedPost()
        }
    }

    private fun displaySelectedPost() {
        val titleTextView: TextView = requireView().findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = requireView().findViewById(R.id.descriptionTextView)
        val postImageView: ImageView = requireView().findViewById(R.id.imageView)

        titleTextView.text = selectedPost.title
        descriptionTextView.text = selectedPost.description

        Glide.with(requireContext()).load(selectedPost.picture).into(postImageView)
    }
}