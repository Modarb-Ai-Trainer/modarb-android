import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemExerciseTypeBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data

class ExercisesAdapter(private var context: Context) :
    PagingDataAdapter<Data, ExercisesAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var selectedItems: HashMap<String, Data> = HashMap()

    inner class ViewHolder(private val binding: ItemExerciseTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data?) {
            item?.let { data ->
                // TODO uncomment this  
                ViewUtils.loadImage(context, data.coverImage, binding.exerciseImage)
                binding.exerciseName.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseTypeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    fun updateData(lifecycleOwner: LifecycleOwner, newData: List<Data>) {
        submitData(lifecycleOwner.lifecycle, PagingData.from(newData))
    }

    fun clearData(lifecycleOwner: LifecycleOwner) {
        submitData(lifecycleOwner.lifecycle, PagingData.empty())
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
