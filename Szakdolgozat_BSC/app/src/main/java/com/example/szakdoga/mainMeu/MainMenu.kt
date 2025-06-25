package com.example.szakdolgozatapp.main_menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.szakdoga.R
import com.example.szakdoga.databinding.MainMenuBinding
import com.example.szakdoga.massageBoard.MassageBoardActivity
import com.example.szakdoga.videoPage.VideoPageActivity
import com.example.szakdolgozatapp.sign_in.SignInMain
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class MainMenu : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: MainMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mAuth = FirebaseAuth.getInstance()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("112457447458-s7c3ksq8gsprm8koeser7jiueurmh5jf.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        val textView = findViewById<TextView>(R.id.name)
        val profImg = findViewById<ImageView>(R.id.prof_img)
        val auth = Firebase.auth
        val user = auth.currentUser



        if (user != null) {
            val photoUri = user.photoUrl
            if(photoUri != null){
                Picasso.get().load(photoUri).into(profImg)
            }

            val userName = user.displayName
            textView.text = "Welcome, " + userName

        } else {
            //TODO: When user is not signed in
        }

        val sign_out_button = findViewById<Button>(R.id.sign_out_button)
        sign_out_button.setOnClickListener {
            signOutAndStartSignInActivity()
        }

        val massageBoardButton = findViewById<Button>(R.id.massageb_button)
        massageBoardButton.setOnClickListener{
                goToMassageBoard()
        }
        val vidoHubButton = findViewById<Button>(R.id.videos_button)
        vidoHubButton.setOnClickListener{
            goToVideos()
        }


    }
    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            val intent = Intent(this@MainMenu, SignInMain::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun goToMassageBoard(){

       val intent = Intent(this, MassageBoardActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun goToVideos(){
        val intent = Intent(this, VideoPageActivity::class.java)
        startActivity(intent)
        finish()
    }
}