package com.example.szakdoga.videoPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.szakdoga.R
import com.example.szakdoga.databinding.VideoActivityMenuBinding
import com.example.szakdoga.videoPage.channel.ChannelFragment
import com.example.szakdoga.videoPage.playList.PlayListFragment
import com.example.szakdoga.videoPage.video.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.invoke.VarHandle

class VideoPageActivity : AppCompatActivity() {
    private lateinit var binding: VideoActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VideoActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_video -> {

                    replaceFragment(VideoFragment())
                    true
                }
                R.id.navigation_playlist -> {

                    replaceFragment(PlayListFragment())
                    true
                }
                R.id.navigation_channel -> {

                    replaceFragment(ChannelFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_video, fragment)
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_video,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.video_search){
            return false
        }
        return super.onOptionsItemSelected(item)
    }
}