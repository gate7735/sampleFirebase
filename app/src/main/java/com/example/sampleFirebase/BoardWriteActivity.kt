package com.example.sampleFirebase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BoardWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)
        val writeUpBtn = findViewById<Button>(R.id.btn_write_up)
       writeUpBtn.setOnClickListener {
            val writeUpEditText = findViewById<EditText>(R.id.et_write_up)
            // Write a message to the database
            val database = Firebase.database
            val myRef = database.getReference("message")

            myRef.push().setValue(
                Model(writeUpEditText.text.toString(), "body")
            )
        }
    }
}