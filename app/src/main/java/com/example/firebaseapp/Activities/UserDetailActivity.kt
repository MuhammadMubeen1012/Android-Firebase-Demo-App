package com.example.firebaseapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebaseapp.Models.UserModel
import com.example.firebaseapp.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserDetailActivity : AppCompatActivity() {

    private lateinit var idView: TextView
    private lateinit var nameView: TextView
    private lateinit var pswView: TextView
    private lateinit var updateBTN: Button
    private lateinit var deleteBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        idView = findViewById(R.id.idTV)
        nameView = findViewById(R.id.nameTV)
        pswView = findViewById(R.id.pswTV)
        updateBTN = findViewById(R.id.updateButton)
        deleteBTN = findViewById(R.id.deleteButton)

        idView.text = intent.getStringExtra("userID")
        nameView.text = intent.getStringExtra("userName")
        pswView.text = intent.getStringExtra("userPassword")

        updateBTN.setOnClickListener{
            openUpdateDialog(intent.getStringExtra("userName"), intent.getStringExtra("userPassword"))
        }

        deleteBTN.setOnClickListener{
            deleteData(intent.getStringExtra("userID").toString())
        }
    }

    private fun deleteData(id: String) {
        val db = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val deleted = db.removeValue()

        deleted
            .addOnSuccessListener {
                Toast.makeText(this, "User Deleted" , Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(this,UsersData::class.java)
                finish()
                startActivity(intent)
            }
            .addOnFailureListener{
                Toast.makeText(this, "Error: ${it.message}" , Toast.LENGTH_LONG).show()
            }
    }

    private fun openUpdateDialog(name: String?, password: String?) {
        val dialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.update_user,null)

        dialog.setView(dialogView)

        val etName = dialogView.findViewById<EditText>(R.id.updateNameET)
        val etPassword = dialogView.findViewById<EditText>(R.id.updatePasswordET)
        val btnSaveUpdated = dialogView.findViewById<Button>(R.id.saveUpdatedDataBTN)

        etName.setText(intent.getStringExtra("userName"))
        etPassword.setText(intent.getStringExtra("userPassword"))

        dialog.setTitle("Updating ${etName} ")

        val alertDialog = dialog.create()
        alertDialog.show()

        btnSaveUpdated.setOnClickListener{
            updateUser(intent.getStringExtra("userID").toString(),etName.text.toString(),etPassword.text.toString())
            alertDialog.dismiss()
        }


    }

    private fun updateUser(id: String,name: String, password: String) {
        val db = FirebaseDatabase.getInstance().getReference("Users").child(id)
        val userModel = UserModel()
        userModel.id = id
        userModel.username = name
        userModel.password = password

        db.setValue(userModel)

        Toast.makeText(this,"Updated",Toast.LENGTH_LONG).show()

        idView.text = id
        nameView.text = name
        pswView.text = password

    }
}