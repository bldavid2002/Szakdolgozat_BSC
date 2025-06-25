package com.example.szakdoga.massageBoard



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.szakdoga.R
import com.example.szakdoga.databinding.PostMainActivityBinding
import com.example.szakdolgozatapp.main_menu.MainMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MassageBoardActivity : AppCompatActivity(),PopUpInterface ,PostClickListener ,LIkeDislikeListener{
    private lateinit var binding: PostMainActivityBinding
    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var postRecycleView: RecyclerView
    private lateinit var postAdapter: MessageBoardAdapter
    private  var postList = ArrayList<Post>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PostMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        postRecycleView = binding.myRecyclerView
        postRecycleView.layoutManager = LinearLayoutManager(this)

        val addButtion = findViewById<Button>(R.id.add_Button)
        addButtion.setOnClickListener {
            replaceFragment()
        }


        dataFetching()

    }

    private fun replaceFragment() {
        val myFragment = AddPostFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_Container, myFragment)
            .commit()
    }

    override fun onPopupSuccesFullUpload() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_Container)
        if (fragment is AddPostFragment) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
            Toast.makeText(this, "LEFUTOTT A BEZÁRÁS", Toast.LENGTH_SHORT).show()
        }
    }




    private fun dataFetching(){
        val databaseReference = firebaseDatabase.getReference("Posts")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val post: Post? = postSnapshot.getValue(Post::class.java)
                    post?.let { postList.add(it) }
                }
                updateUI()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                TODO()
            }
        })
    }
    override fun onPostClick(post: Post) {
        Log.d("Navigation", "Post clicked: ${post.title}")


        val fragment = OpenPostFragment.newInstance(post)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_Container, fragment)
            .addToBackStack(null)
            .commit()

    }

    private fun updateUI() {
        if(!::postAdapter.isInitialized) {
            Log.d("Navigation", "Initializing adapter")
            postAdapter = MessageBoardAdapter(this@MassageBoardActivity, postList,this@MassageBoardActivity, this@MassageBoardActivity)
            postRecycleView.adapter = postAdapter

        }else{
            postAdapter.notifyDataSetChanged()
        }
    }


    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentById(R.id.frame_Container)

        if (fragment != null) {

            fragmentManager.beginTransaction().remove(fragment).commit()
        } else {

            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }


        super.onBackPressed()
    }

    private  fun updateFirebaseLikeDisLikeCount(post: Post) {
        val databaseReference = firebaseDatabase.getReference("Posts").child(post.postKey!!)
        databaseReference.child("likeCount").setValue(post.likeCounter)
        databaseReference.child("dislikeCounter").setValue(post.dislikeCounter)
    }


    override fun onLikeDislikeClicked(post: Post) {
        updateFirebaseLikeDisLikeCount(post)
    }








}

