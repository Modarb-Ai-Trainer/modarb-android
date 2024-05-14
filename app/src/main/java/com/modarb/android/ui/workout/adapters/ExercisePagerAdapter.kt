package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.modarb.android.R
import com.modarb.android.ui.home.helpers.WorkoutData
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class ExercisePagerAdapter(private val context: Context) : PagerAdapter() {

    private val exercises = WorkoutData.getTodayWorkout()!!.exercises


    override fun getCount(): Int = exercises.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    lateinit var viewPager: ViewPager
    lateinit var emptyAdapter: EmptyPagerAdapter
    private lateinit var dotsIndicator: WormDotsIndicator

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_exercise, container, false)

        val exerciseTitle: TextView = view.findViewById(R.id.excersiceName)
        val exerciseCount: TextView = view.findViewById(R.id.exerciseCount)
        val exerciseDesc: TextView = view.findViewById(R.id.exerciseDesc)
        val exerciseSetCount: TextView = view.findViewById(R.id.exerciseSetCount)

        var curSetCount = 1

        val exercise = exercises[position]
        exerciseTitle.text = exercise.name
        exerciseCount.text = exercise.reps.toString() + " reps"
        exerciseDesc.text = exercise.instructions
        exerciseSetCount.text = "${curSetCount} / " + exercise.sets.toString() + " sets"

        dotsIndicator = view.findViewById(R.id.worm_dots_indicator)
        viewPager = view.findViewById(R.id.dummy_pager)
        emptyAdapter = EmptyPagerAdapter(exercise.sets)
        viewPager.adapter = emptyAdapter
        dotsIndicator.attachTo(viewPager)

        for (i in 0..2) {
            val currentItem = viewPager.currentItem
            val adapterCount = emptyAdapter.count - 1
            if (currentItem < adapterCount) {
                viewPager.setCurrentItem(currentItem + 1, true)
                dotsIndicator.pager!!.setCurrentItem(currentItem + 1, true)
                Log.e("amr", "incrementSetCount: ${dotsIndicator.pager!!.currentItem}")
            }
        }
        container.addView(view)
        return view
    }


    fun incrementSetCount() {

    }

    class EmptyPagerAdapter(private val pageCount: Int) : PagerAdapter() {

        override fun getCount(): Int = pageCount

        override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}
