package com.example.szakdolgozatapp.sign_in

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.szakdoga.databinding.SignInScreenBinding
import com.example.szakdolgozatapp.main_menu.MainMenu
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInMain : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: SignInScreenBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignInScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("112457447458-s7c3ksq8gsprm8koeser7jiueurmh5jf.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.signInButton.setOnClickListener{
            signIn()
        }

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            finish()
        }
    }
    companion object{
        const val RC_SIGN_IN = 1001
    }

    private fun signIn() {


        val signInIntent = googleSignInClient.signInIntent


        startActivityForResult(signInIntent, RC_SIGN_IN)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(ContentValues.TAG, "FirebaseAuthWithGoolge:"+account.id)
                firebaseAuthWithGoogle(account.idToken!!)

            }catch (e: ApiException){
                Log.w("Faild","Goole sign in Faild")
            }
        }
    }



    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainMenu::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }




}
