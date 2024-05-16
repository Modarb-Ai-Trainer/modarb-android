package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.modarb.android.databinding.ItemExerciseBinding
import com.modarb.android.ui.home.helpers.WorkoutData

class ExercisePagerAdapter(private val context: Context, private val viewPager: ViewPager) :
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

        binding.root.tag = "view$position"
        container.addView(binding.root)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun incSetCount(position: Int): Boolean {
        val exercise = exercises.getOrNull(position)
        if (exercise!!._currentSetCount >= exercise.sets) return false
        val currentView = viewPager.findViewWithTag<View>("view${viewPager.currentItem}")
        val binding = ItemExerciseBinding.bind(currentView)
        binding.exerciseSetCount.text = "${++exercise._currentSetCount} / ${exercise.sets} set"
        notifyDataSetChanged()
        return true
    }

    fun isTimedExercise(position: Int): Boolean {
        val exercise = exercises.getOrNull(position)
        return (exercise?.duration != 0)
    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }


}
