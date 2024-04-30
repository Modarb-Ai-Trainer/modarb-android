package com.modarb.android.ui.home.ui.more.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.models.User

class ActiveUsersAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<ActiveUsersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val activeUsersTextView: TextView = itemView.findViewById(R.id.activeUsersTextView)
        val challengeTextView: TextView = itemView.findViewById(R.id.challengeTextView)
        val activeUserView: ImageView = itemView.findViewById(R.id.activeUserView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_active_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.activeUsersTextView.text = user.name
        holder.challengeTextView.text = user.challenge
        holder.activeUserView.setImageResource(user.imageResource)
    }

    override fun getItemCount(): Int = userList.size
}