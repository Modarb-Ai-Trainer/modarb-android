package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.plan.models.Day


class DaysTimeLineAdapter(private val itemList: ArrayList<Day>) :
    RecyclerView.Adapter<DaysTimeLineAdapter.ItemViewHolder>() {
    private var isTheCurrentDayFound: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_timeline_days, parent, false)
        return ItemViewHolder(view)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayTitle: TextView = itemView.findViewById(R.id.dayText)
        val leftLine: View = itemView.findViewById(R.id.leftDashed)
        val rightLine: View = itemView.findViewById(R.id.rightDashed)
        var endImage: ImageView = itemView.findViewById(R.id.endImage)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dayData = itemList[position]

        // Show end image
        if (position == itemList.size - 1) {
            holder.endImage.visibility = View.VISIBLE
            holder.dayTitle.visibility = View.INVISIBLE
            holder.rightLine.visibility = View.GONE
            return
        }
        if (position == 0) {
            holder.leftLine.visibility = View.GONE
        }
        holder.dayTitle.text = "${position + 1}D"

        holder.dayTitle.background =
            ContextCompat.getDrawable(holder.dayTitle.context, R.drawable.rounded_textview)
        if (!dayData.is_done) {
            // This is the current day
            if (!isTheCurrentDayFound) {
                holder.dayTitle.background = ContextCompat.getDrawable(
                    holder.dayTitle.context, R.drawable.rounded_selected_textview
                )
            }
            isTheCurrentDayFound = true
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
