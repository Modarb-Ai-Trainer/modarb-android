//package com.modarb.android.ui.home.ui.nutrition.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.yourappname.databinding.ItemMealBinding
//import com.modarb.android.R
//import com.modarb.android.databinding.ItemMealBinding
//import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.Data
//import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.Day
//import com.modarb.android.ui.home.ui.nutrition.domain.models.today_meals.TodayMealsResponse
//
//class TodayMealAdapter(private val mealList: List<Day>) : RecyclerView.Adapter<TodayMealAdapter.MealViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemMealBinding.inflate(inflater, parent, false)
//        return MealViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
//        val mealItem = mealList[position]
//        holder.bind(mealItem)
//    }
//
//    override fun getItemCount(): Int {
//        return mealList.size
//    }
//
//    class MealViewHolder(private val binding: ItemMealBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(mealItem: MealItem) {
//            binding.mealName.text = mealItem.name
//            binding.mealTake.text = "${mealItem.quantity}g"
//            binding.imageView25.setImageResource(R.mipmap.ic_launcher)
//        }
//    }
//}
