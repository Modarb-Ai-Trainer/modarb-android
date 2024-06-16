package com.modarb.android.ui.workout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemEquipmentBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.plan.domain.models.Equipment

class EquipmentAdapter(private val context: Context, private val data: List<Equipment>) :
    RecyclerView.Adapter<EquipmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemEquipmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemEquipmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(context: Context, item: Equipment) {
            binding.textView.text = item.name
            ViewUtils.loadImage(context, item.image, binding.imageView)
        }
    }
}
