package com.example.firebaseapp.Activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.Adapters.UsersAdapter
import com.example.firebaseapp.Models.UserModel
import com.example.firebaseapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class UsersData : AppCompatActivity() {

    private lateinit var usersRV: RecyclerView
    private lateinit var loadingTV: TextView
    private lateinit var usersList: ArrayList<UserModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_data)

        usersRV = findViewById(R.id.recyclerView)
        loadingTV = findViewById(R.id.loadingTV)
        usersList = arrayListOf(UserModel())

        usersRV.layoutManager = LinearLayoutManager(this)
        usersRV.setHasFixedSize(true)

        getUsers()

    }

    private fun getUsers() {
        usersRV.visibility = View.GONE
        loadingTV.visibility = View.VISIBLE

        val db = FirebaseDatabase.getInstance().getReference("Users")

        db.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                usersList.clear()
                if(snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue(UserModel::class.java)
                        usersList.add(userData!!)
                    }

                    val adapter = UsersAdapter(usersList)
                    usersRV.adapter = adapter

                    adapter.setOnItemClickListener(object : UsersAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent: Intent = Intent(this@UsersData,UserDetailActivity::class.java)

                            //data
                            intent.putExtra("userID" , usersList[position].id)
                            intent.putExtra("userName" , usersList[position].username)
                            intent.putExtra("userPassword" , usersList[position].password)

                            startActivity(intent)
                        }
                    })
                    usersRV.visibility = View.VISIBLE
                    loadingTV.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}