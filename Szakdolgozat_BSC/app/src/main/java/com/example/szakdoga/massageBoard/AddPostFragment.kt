package com.example.szakdoga.massageBoard

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.szakdoga.R

import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class AddPostFragment : Fragment(){
    private val auth = Firebase.auth
    private var  pickImgUrl: Uri? = null
    private val REQUEST_CODE: Int = 2
    private lateinit var userImagePopUp: ImageView
    private lateinit var imageSelect: View
    private lateinit var addPost: View
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var popUpImage: ImageView
    private final val PRequestCode = 2



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pop_up_add_post, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageSelect = view.findViewById<View>(R.id.imga_select_view)
        addPost = view.findViewById<View>(R.id.add_Post)
        title = view.findViewById<EditText>(R.id.popup_title)
        description = view.findViewById<EditText>(R.id.poup_description)
        popUpImage = view.findViewById<ImageView>(R.id.selectedImage)
        userImagePopUp = view.findViewById<ImageView>(R.id.popup_user_image)
        val currentUser = auth.currentUser



        Picasso.get().load(currentUser?.photoUrl).into(userImagePopUp)
        imageSelect.setOnClickListener{
            checkRequestPermission()

        }
        addPost.setOnClickListener{
            if(!title.text.isNullOrEmpty() && !description.text.isNullOrEmpty() && pickImgUrl != null){

                val storageReference = FirebaseStorage.getInstance().getReference().child("blog_image")
                val imageFilePath = storageReference.child(pickImgUrl?.lastPathSegment.toString())

                imageFilePath.putFile(pickImgUrl!!)
                    .addOnSuccessListener { taskSnapshot ->
                        imageFilePath.downloadUrl
                            .addOnSuccessListener { url ->
                                val imgDownloadLink = url.toString()
                                val postData: Post =  Post(title.text.toString(),
                                    description.text.toString(),
                                    imgDownloadLink.trimEnd(),
                                    currentUser!!.uid,
                                    currentUser.photoUrl.toString())

                                addingPost(postData)
                            }
                    }.addOnFailureListener { e ->
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

            }else{


                Toast.makeText(requireContext(),"A Cím vagy a Leírás hiányzik",Toast.LENGTH_SHORT).show()
            }
        }

    }





    private fun checkRequestPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(requireContext(), "Kérem Engedélyezze a hozzáférési engedélyt", Toast.LENGTH_SHORT).show()

        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                PRequestCode)
        }

        openGallery()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            pickImgUrl = data.data
            popUpImage.scaleType = ImageView.ScaleType.CENTER_CROP
            if (pickImgUrl != null) {
                popUpImage.setImageURI(pickImgUrl)
            } else {
                Toast.makeText(requireContext(), "Nem jó  uri", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun addingPost(postData: Post) {
        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Posts").push()

        val key = myRef.key
        postData.postKey = key


        myRef.setValue(postData).addOnSuccessListener {
            Toast.makeText(requireContext(), "Sikeres Üzenet feltöltés", Toast.LENGTH_SHORT).show()
            (activity as MassageBoardActivity).onPopupSuccesFullUpload()
        }.addOnCanceledListener {
            Toast.makeText(requireContext(), "A művelet megszakítva", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(requireContext(),"Hiba történt ",Toast.LENGTH_SHORT).show()
        }



    }



}
