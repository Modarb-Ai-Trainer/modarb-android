package com.modarb.android.ui.home.ui.more.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.models.ChallengeModel

class ChallengeAdapter(private val dataList: List<ChallengeModel>) :
    RecyclerView.Adapter<ChallengeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_challenge, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val challenge = dataList[position]
        holder.itemView.setBackgroundResource(challenge.backgroundResId)
        holder.bind(challenge.title)
    }



    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val challengeTitleTextView: TextView = itemView.findViewById(R.id.challengeTitleTextView)

        fun bind(data: String) {
            challengeTitleTextView.text = data
        }
    }
}
