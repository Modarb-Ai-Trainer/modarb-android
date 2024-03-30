package com.modarb.android.ui.workout.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.modarb.android.R
import com.modarb.android.ui.workout.fragments.WorkoutFragment

class MyTrainerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trainer)

        val frag1 : Fragment = WorkoutFragment()

        val fragTrans : FragmentTransaction = supportFragmentManager.beginTransaction()
        fragTrans.replace(R.id.frame1, frag1).commit()
    }
}

