package com.modarb.android.ui.home.ui.nutrition.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemNutritionMealBinding
import com.modarb.android.ui.home.ui.nutrition.models.NutritionDataModel

class NutritionMealAdapter(private var dataList: List<NutritionDataModel>) :
    RecyclerView.Adapter<NutritionMealAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNutritionMealBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(
        private val binding: ItemNutritionMealBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {

        }

        fun bind(data: NutritionDataModel) {
            binding.textViewDayName.text = data.dayName
            binding.recyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerView.adapter = NutritionMealDayAdapter(data.meals)

        }
    }
}