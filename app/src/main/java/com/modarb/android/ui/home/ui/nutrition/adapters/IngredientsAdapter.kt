package com.modarb.android.ui.home.ui.nutrition.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.modarb.android.R
import com.modarb.android.databinding.IngredientDetailsViewBinding
import com.modarb.android.databinding.ItemIngredientsBinding
import com.modarb.android.ui.home.ui.nutrition.models.ingredients.Data

class IngredientsAdapter(
    private val mealType: String,
    private val context: Context,
    private val isAdd: Boolean
) :
    PagingDataAdapter<Data, IngredientsAdapter.IngredientViewHolder>(DIFF_CALLBACK) {
    private var selectedItems: HashMap<String, Data> = HashMap()

    class IngredientViewHolder(val binding: ItemIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            ItemIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        with(holder.binding) {
            name.text = ingredient!!.name
            details.text = ingredient.serving_size.toString() + "g"

            addBtn.setOnClickListener {}
            holder.itemView.setOnClickListener {
                initAddIngredientsBottomSheet(ingredient, position)
            }

            val isSelected = selectedItems.contains(ingredient.id)
            root.isActivated = isSelected
            val resId =
                if (isSelected) R.drawable.baseline_check_circle_24 else R.drawable.baseline_add_circle_outline_24
            remove.visibility = if (isSelected) View.VISIBLE else View.GONE
            addBtn.setImageResource(resId)
            remove.setOnClickListener {
                removeSelected(ingredient.id, position)
            }
            if (!isAdd) addBtn.visibility = View.GONE
        }
    }

    fun removeSelected(itemId: String, position: Int) {
        if (!selectedItems.contains(itemId)) return
        selectedItems.remove(itemId)
        notifyItemChanged(position)
    }

    fun markAsSelected(itemId: String, position: Int) {
        if (selectedItems.contains(itemId)) return
        selectedItems[itemId] = getItem(position)!!
        getSelectedItems()
        notifyItemChanged(position)
    }

    fun updateData(lifecycleOwner: LifecycleOwner, newData: List<Data>) {
        submitData(lifecycleOwner.lifecycle, PagingData.from(newData))
    }

    private fun getSelectedItems() {
        Log.d("selectedItems", selectedItems.toString())
    }

    fun clearSelectedData() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedData(): List<Data> {
        val selectedDataList = mutableListOf<Data>()

        for ((_, value) in selectedItems) {
            selectedDataList.add(value)
        }

        return selectedDataList
    }

    fun getSelectedIngredientIds(): List<String> {
        val selectedIngredientIds = mutableListOf<String>()
        for ((key, _) in selectedItems) {
            selectedIngredientIds.add(key)
        }
        return selectedIngredientIds
    }

    private fun initAddIngredientsBottomSheet(data: Data, position: Int) {
        val ingreadientsBottomSheet = BottomSheetDialog(context)

        val binding = IngredientDetailsViewBinding.inflate(LayoutInflater.from(context))
        ingreadientsBottomSheet.setContentView(binding.root)

        ingreadientsBottomSheet.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        ingreadientsBottomSheet.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)
                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        binding.btnClose.setOnClickListener { ingreadientsBottomSheet.hide() }

        binding.tvTitle.text = data.name
        binding.tvServingSize.text = data.serving_size.toString() + "g"
        binding.tvNumberOfServings.text = "${data.servings_count} tbsp"
        binding.tvCalories.text = "${data.calories} kcal"
        binding.tvCarbs.text = "${data.carbs}"
        binding.tvProtein.text = "${data.proteins} mg"
        binding.tvFats.text = "${data.fats} g"

        binding.btnAddToBreakfast.text = "Add to ${mealType}"

        binding.btnAddToBreakfast.setOnClickListener {
            markAsSelected(data.id, position)
            ingreadientsBottomSheet.hide()
        }
        ingreadientsBottomSheet.show()
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }

}