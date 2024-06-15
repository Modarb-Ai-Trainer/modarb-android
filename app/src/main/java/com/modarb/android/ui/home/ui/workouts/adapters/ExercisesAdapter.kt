import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.databinding.ItemExerciseTypeBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.home.ui.plan.domain.models.Equipment
import com.modarb.android.ui.home.ui.plan.domain.models.Exercise
import com.modarb.android.ui.home.ui.plan.domain.models.ExpectedDurationRange
import com.modarb.android.ui.home.ui.plan.domain.models.Media
import com.modarb.android.ui.home.ui.plan.domain.models.Primary
import com.modarb.android.ui.home.ui.plan.domain.models.Secondary
import com.modarb.android.ui.home.ui.plan.domain.models.TargetMuscles
import com.modarb.android.ui.home.ui.plan.domain.models.allExercises.Data
import com.modarb.android.ui.workout.activities.ExerciseInfoActivity

class ExercisesAdapter(private var context: Context) :
    PagingDataAdapter<Data, ExercisesAdapter.ViewHolder>(DIFF_CALLBACK) {


    inner class ViewHolder(private val binding: ItemExerciseTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Data?) {
            item?.let { data ->
                ViewUtils.loadImage(context, data.media.url, binding.exerciseImage)
                binding.exerciseName.text = data.name
            }

            val list: ArrayList<Equipment> = ArrayList()

            for (i in 0 until item!!.equipments.size) {
                list.add(Equipment(1, "1", item.equipments[i].image, item.equipments[i].name))
            }
            itemView.setOnClickListener {
                // TODO refactor this
                WorkoutData.selectedExercise = Exercise(
                    1,
                    item.id,
                    item.benefits,
                    item.category,
                    item.duration,
                    list.toList(),
                    ExpectedDurationRange(
                        item.expectedDurationRange.max, item.expectedDurationRange.min
                    ),
                    item.instructions,
                    Media(item.media.type, item.media.url),
                    item.name,
                    item.reps,
                    item.sets,
                    TargetMuscles(
                        Primary(
                            1,
                            "1",
                            item.targetMuscles.primary.image,
                            item.targetMuscles.primary.name
                        ), Secondary(
                            1,
                            "1",
                            item.targetMuscles.secondary.image,
                            item.targetMuscles.secondary.name
                        )
                    ),
                    0,
                    false,
                    null,
                    false,
                    0
                )
                val i = Intent(context, ExerciseInfoActivity::class.java)
                i.putExtra("info", true)
                context.startActivity(i)

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
