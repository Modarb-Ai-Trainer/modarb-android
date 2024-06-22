package com.modarb.android.ui.home.ui.nutrition.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.databinding.ItemNutritionProgramBinding
import com.modarb.android.ui.home.ui.nutrition.OnPlanItemClickListener
import com.modarb.android.ui.home.ui.nutrition.domain.models.all_meals_plan.Data

class NutritionProgramsAdapter(
    private val list: List<Data>,
    private val listener: OnPlanItemClickListener
) : RecyclerView.Adapter<NutritionProgramsAdapter.NutritionViewHolder>() {

    inner class NutritionViewHolder(private val binding: ItemNutritionProgramBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(program: Data) {
            //TODO Uncomment this
            //ViewUtils.loadImage(context, program.image, binding.image)
            if (position % 4 == 0) {
                binding.image.setImageResource(R.drawable.rounded_gradient_rectangle2)
            } else if (position % 4 == 1) {
                binding.image.setImageResource(R.drawable.rounded_gradient_rectangle3)
            } else if (position % 4 == 2) {
                binding.image.setImageResource(R.drawable.rounded_gradient_rectangle4)
            } else if (position % 4 == 3) {
                binding.image.setImageResource(R.drawable.rounded_gradient_rectangle5)
            }
            binding.name.text = program.level
            binding.desc.text = program.description
            binding.planView.setOnClickListener {
                listener.onPlanItemClick(program)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionViewHolder {
        val binding =
            ItemNutritionProgramBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NutritionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NutritionViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
