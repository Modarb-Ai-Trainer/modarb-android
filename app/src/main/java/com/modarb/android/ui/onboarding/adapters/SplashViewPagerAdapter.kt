package com.modarb.android.ui.onboarding.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R

class SplashViewPagerAdapter(private val views: List<View>) :
    RecyclerView.Adapter<SplashViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = views[position]
        holder.bind(view)
    }

    override fun getItemCount(): Int = views.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(view: View) {
            val container = itemView.findViewById<FrameLayout>(R.id.container)
            container.removeAllViews()
            container.addView(view)
        }
    }
}
