package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.modarb.android.databinding.ItemExerciseBinding
import com.modarb.android.ui.home.helpers.WorkoutData
import com.modarb.android.ui.workout.ExerciseListener

class ExercisePagerAdapter(private val context: Context, var listener: ExerciseListener) :
    PagerAdapter() {

    private val exercises = WorkoutData.getTodayWorkout()?.exercises ?: emptyList()

    override fun getCount(): Int = exercises.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val binding = ItemExerciseBinding.inflate(inflater, container, false)

        val exercise = exercises[position]
        binding.exerciseName.text = exercise.name
        binding.exerciseCount.text = "${exercise.reps} reps"
        binding.exerciseDesc.text = exercise.instructions
        binding.exerciseSetCount.text = "${exercise._currentSetCount} / ${exercise.sets} set"
        handleCloseBtn(binding)
        binding.root.tag = "view$position"
        container.addView(binding.root)

        return binding.root
    }

    private fun handleCloseBtn(binding: ItemExerciseBinding) {
        binding.exitButton.setOnClickListener {
            listener.onCloseListener()
        }
    }


    /**
     * this function responsible for increment set count
     * it will return false if the user completed all of the sets
     * use it for workout activity navigation
     */
    @SuppressLint("SetTextI18n")
    fun incSetCount(position: Int, currentView: View): Boolean {
        val exercise = exercises[position]
        if (exercise._currentSetCount >= exercise.sets) return false
        val binding = ItemExerciseBinding.bind(currentView)
        binding.exerciseSetCount.text = "${++exercise._currentSetCount} / ${exercise.sets} set"
        notifyDataSetChanged()
        return true
    }

    fun handleEndExercise() {

    }

    fun logExercise(position: Int) {
        val exercise = exercises[position]
        Log.d("Exercise", "${exercise.sets} sets, ${exercise._currentSetCount}")
    }

    fun isExerciseDone(position: Int): Boolean {
        val exercise = exercises[position]
        Log.d(
            exercise.name + " pos= " + "${position}",
            "${exercise._currentSetCount} / ${exercise.sets}"
        )
        return (exercise._currentSetCount >= exercise.sets)
    }

    fun isTimedExercise(position: Int): Boolean {
        val exercise = exercises.getOrNull(position)
        return (exercise?.duration != 0)
    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }


}
