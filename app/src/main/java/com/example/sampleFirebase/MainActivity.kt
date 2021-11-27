package com.example.sampleFirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sampleFirebase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize Firebase Auth
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val joinButton = binding.btnJoin
        val logoutButton = binding.btnLogout
        val loginButton = binding.btnLogin
        joinButton.setOnClickListener {
//            val emailEditText = findViewById<EditText>(R.id.et_email)
//            val pwdEditText = findViewById<EditText>(R.id.et_pwd)
            val emailEditText = binding.etEmail
            val pwdEditText = binding.etPwd
            auth.createUserWithEmailAndPassword(
                emailEditText.text.toString(),
                pwdEditText.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "createUserWithEmail:success",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        logoutButton.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()
        }
        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.etEmail.text.toString(),
                binding.etPwd.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Login - ${auth.currentUser?.uid.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, BoardListActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Login - Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}