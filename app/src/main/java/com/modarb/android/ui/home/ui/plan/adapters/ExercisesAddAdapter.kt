import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemExerciseSelectionDetailsBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data

class ExercisesAddAdapter(private var context: Context, private val isAdd: Boolean) :
    PagingDataAdapter<Data, ExercisesAddAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var selectedItems: HashMap<String, Data> = HashMap()

    inner class ViewHolder(private val binding: ItemExerciseSelectionDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data?) {
            item?.let { data ->

                ViewUtils.loadImage(context, data.media.url, binding.exerciseImage)

                binding.exerciseTitle.text = data.name
                binding.exerciseDesc.text = data.benefits

                if (isAdd) {
                    val isSelected = selectedItems.contains(data.id)
                    binding.root.isActivated = isSelected
                    binding.overlay.visibility =
                        if (isSelected) ViewGroup.VISIBLE else ViewGroup.INVISIBLE
                    binding.checkMark.visibility =
                        if (isSelected) ViewGroup.VISIBLE else ViewGroup.INVISIBLE

                    binding.root.setOnClickListener {
                        toggleSelection(data.id, adapterPosition)
                    }
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseSelectionDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun toggleSelection(itemId: String, position: Int) {
        if (selectedItems.contains(itemId)) {
            selectedItems.remove(itemId)

        } else {
            selectedItems[itemId] = getItem(position)!!
        }
        getSelectedItems()
        notifyItemChanged(position)
    }

    private fun getSelectedItems() {
        Log.d("selectedItems", selectedItems.toString())
    }

    fun updateData(lifecycleOwner: LifecycleOwner, newData: List<Data>) {
        submitData(lifecycleOwner.lifecycle, PagingData.from(newData))
    }

    fun clearData(lifecycleOwner: LifecycleOwner) {
        submitData(lifecycleOwner.lifecycle, PagingData.empty())
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

    fun getSelectedExerciseId(): List<String> {
        val selectedExerciseIds = mutableListOf<String>()
        for ((key, _) in selectedItems) {
            selectedExerciseIds.add(key)
        }
        return selectedExerciseIds
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
