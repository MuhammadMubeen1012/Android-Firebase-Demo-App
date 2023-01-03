package com.example.firebaseapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.firebaseapp.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var insertData: Button
    private lateinit var getData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().reference
        insertData = findViewById(R.id.addUsersBTN)
        getData = findViewById(R.id.getUsersBTN)

        insertData.setOnClickListener{
            val intent: Intent = Intent(this, InsertData::class.java)
            startActivity(intent)
            finish()
        }

        getData.setOnClickListener{
            val intent: Intent = Intent(this, UsersData::class.java)
            startActivity(intent)
            finish()
        }

    }
}