package com.example.firebaseapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseapp.Adapters.UsersAdapter.*
import com.example.firebaseapp.Models.UserModel
import com.example.firebaseapp.R

class UsersAdapter (private val usersList: ArrayList<UserModel>): RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    //1
    private lateinit var listner: onItemClickListener

    //2
    interface onItemClickListener{
        fun onItemClick(position:Int)
    }

    //3
    fun setOnItemClickListener(clickListener:onItemClickListener){
        listner = clickListener
    }

    //6
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflating design
        val usersView = LayoutInflater.from(parent.context).inflate(R.layout.user_list,parent,false)
        return ViewHolder(usersView,listner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getting current user
        val user = usersList[position]
        holder.userNameTV.text = user.username
    }


    override fun getItemCount(): Int {
        return usersList.size
    }

    //4
    class ViewHolder(view: View , clickListener: onItemClickListener): RecyclerView.ViewHolder(view) {
        //initialize the views of layout
        val userNameTV: TextView = view.findViewById(R.id.usernameTV)

        //5
        init {
            view.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}