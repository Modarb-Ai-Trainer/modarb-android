import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.home.ui.workouts.OnBodyPartClickListener
import com.modarb.android.ui.home.ui.workouts.adapters.BodyPartsAdapter
import com.modarb.android.ui.home.ui.workouts.models.BodyParts

class ExerciseLibViewHolder(private val view: View, private val context: Context) :
    RecyclerView.ViewHolder(view) {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BodyPartsAdapter
    private lateinit var exercises: ArrayList<BodyParts>
    private var listener: OnBodyPartClickListener? = null

    fun bind(listener: OnBodyPartClickListener) {
        this.listener = listener
        initRecycleView(view, context)
    }


    private fun initRecycleView(view: View, context: Context) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        exercises = ArrayList()
        for (part in WorkoutData.getBodyParts()) {
            if (part != "All")
                exercises.add(BodyParts(part, R.drawable.rounded_gradient_rectangle))
        }

        adapter = BodyPartsAdapter(exercises, listener!!)
        recyclerView.adapter = adapter
    }
}
