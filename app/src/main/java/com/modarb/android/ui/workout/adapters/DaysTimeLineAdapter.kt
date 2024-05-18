package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.databinding.ItemTimelineDaysBinding
import com.modarb.android.ui.home.ui.plan.domain.models.Day

class DaysTimeLineAdapter(private val itemList: ArrayList<Day>) :
    RecyclerView.Adapter<DaysTimeLineAdapter.ItemViewHolder>() {
    private var isTheCurrentDayFound: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemTimelineDaysBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    class ItemViewHolder(val binding: ItemTimelineDaysBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val dayData = itemList[position]

        // Show end image
        if (position == itemList.size - 1) {
            holder.binding.endImage.visibility = View.VISIBLE
            holder.binding.dayText.visibility = View.INVISIBLE
            holder.binding.rightDashed.visibility = View.GONE
            return
        }
        if (position == 0) {
            holder.binding.leftDashed.visibility = View.GONE
        }
        holder.binding.dayText.text = "${position + 1}D"

        holder.binding.dayText.background =
            ContextCompat.getDrawable(holder.binding.dayText.context, R.drawable.rounded_textview)
        if (!dayData.is_done) {
            // This is the current day
            if (!isTheCurrentDayFound) {
                holder.binding.dayText.background = ContextCompat.getDrawable(
                    holder.binding.dayText.context, R.drawable.rounded_selected_textview
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
