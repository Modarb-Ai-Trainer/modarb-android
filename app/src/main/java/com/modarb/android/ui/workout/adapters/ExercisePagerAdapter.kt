package com.modarb.android.ui.workout.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.modarb.android.databinding.ItemExerciseBinding
import com.modarb.android.ui.helpers.ViewUtils
import com.modarb.android.ui.helpers.WorkoutData
import com.modarb.android.ui.workout.ExerciseListener

class ExercisePagerAdapter(private val context: Context, private var listener: ExerciseListener) :
    PagerAdapter() {

    private val exercises = WorkoutData.getTodayWorkout()?.exercises ?: emptyList()
    private val timedExercise: HashSet<Int> = HashSet()

    override fun getCount(): Int = exercises.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val binding = ItemExerciseBinding.inflate(inflater, container, false)

        val exercise = exercises[position]
        binding.exerciseName.text = exercise.name
        binding.exerciseCount.text = "${exercise.sets} sets x ${exercise.reps} reps"
        binding.exerciseDesc.text = exercise.instructions
        ViewUtils.loadImage(context, exercise.media.url, binding.exerciseGIf)
        //binding.exerciseSetCount.text = "${exercise._currentSetCount} / ${exercise.sets} set"
        handleCloseBtn(binding)
        binding.root.tag = "view$position"

        if (isTimedExercise(position)) {

            binding.exerciseCount.visibility = View.GONE
            //binding.exerciseSetCount.visibility = View.GONE
            binding.timer.visibility = View.VISIBLE
            exercises[position].remainingTime = (exercises[position].duration * 1000).toLong()
            timedExercise.add(position)

            val millisUntilFinished = (exercise.duration * 1000).toLong()
            val minutes = (millisUntilFinished / 1000) / 60
            val seconds = (millisUntilFinished / 1000) % 60
            setSeconds(minutes, seconds, binding.timer)
        }
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
//    @SuppressLint("SetTextI18n")
//    fun incSetCount(position: Int, currentView: View): Boolean {
//        val exercise = exercises[position]
//        if (exercise.reps == 0) return false
//        val binding = ItemExerciseBinding.bind(currentView)
//        if (exercise._currentSetCount + 1 == exercise.sets) {
//            exercises[position].reps--
//            binding.exerciseCount.text = "${exercise.reps} reps"
//            exercises[position]._currentSetCount = 0
//            binding.exerciseSetCount.text = "${exercise._currentSetCount} / ${exercise.sets} set"
//        } else {
//            binding.exerciseSetCount.text = "${++exercise._currentSetCount} / ${exercise.sets} set"
//        }
//        notifyDataSetChanged()
//        return true
//    }


    fun initTimer(position: Int, currentView: View) {
        val binding = ItemExerciseBinding.bind(currentView)

        exercises[position].countDownTimer =
            object : CountDownTimer(exercises[position].remainingTime, 1000) {
                @SuppressLint("DefaultLocale")
                override fun onTick(millisUntilFinished: Long) {
                    exercises[position].remainingTime = millisUntilFinished
                    val minutes = (millisUntilFinished / 1000) / 60
                    val seconds = (millisUntilFinished / 1000) % 60
                    setSeconds(minutes, seconds, binding.timer)
                }

                override fun onFinish() {
                    timerEnded(position)
                }
            }
    }


    @SuppressLint("DefaultLocale")
    private fun setSeconds(minutes: Long, seconds: Long, timer: TextView) {

        val timeString = String.format("%02d:%02d", minutes, seconds)
        timer.text = timeString
    }

    private fun timerEnded(position: Int) {
        exercises[position].isTimeExerciseDone = true
    }

    fun isStarted(position: Int): Boolean {
        val exercise = exercises[position]
        return exercise.isStarted
    }

    fun startTimer(position: Int) {
        exercises[position].countDownTimer!!.start()
        exercises[position].isStarted = true
        notifyDataSetChanged()
    }

    fun pauseTimer(position: Int) {
        val exercise = exercises[position]
        exercise.countDownTimer!!.cancel()
        exercise.isStarted = false
        notifyDataSetChanged()
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
        return (exercise._isDone)
    }

    fun isTimedExerciseDone(position: Int): Boolean {
        return exercises[position].isTimeExerciseDone
    }

    fun isTimedExercise(position: Int): Boolean {
        return timedExercise.find { it == position } != null || exercises[position].duration > 0
    }


    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    fun markDone(currentPosition: Int) {
        exercises[currentPosition]._isDone = true
    }


}
