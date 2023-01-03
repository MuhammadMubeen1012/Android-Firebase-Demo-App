package com.example.firebaseapp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebaseapp.R
import com.example.firebaseapp.Models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertData : AppCompatActivity() {

    private lateinit var unET: EditText
    private lateinit var pswET: EditText
    private lateinit var saveData: Button

    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)

        unET = findViewById(R.id.usernameET)
        pswET = findViewById(R.id.passwordET)
        saveData = findViewById(R.id.saveBTN)

        db = FirebaseDatabase.getInstance().getReference("Users")

        saveData.setOnClickListener{
            addUser()
        }

    }

    private fun addUser(){
        val username = unET.text.toString()
        val password = pswET.text.toString()

        if(username.isEmpty()){
            unET.error = "Please: Enter Data"
        }

        if(password.isEmpty()){
            pswET.error = "Please; Enter Data"
        }

        //generating unique ID
        val userID = db.push().key!!
        val user = UserModel()

        user.id = userID
        user.username = unET.text.toString()
        user.password = pswET.text.toString()

        db.child(userID).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this,"Success: User Added",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(this,"Failure: ${it.message} ",Toast.LENGTH_LONG).show()
            }
    }
}