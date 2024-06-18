package com.modarb.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.adapters.RecoveryPlanAdapter

class InjuryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_injury)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            finish()
        }
        val recoveryPlanList = listOf("JointEase Shoulder Renewal", "Your Comprehensive Guide\nto Healing")
        val imageResources = listOf(
            R.drawable.recovery_plan_img1,
            R.drawable.recovery_plan_img2
        )
        val recyclerView: RecyclerView = findViewById(R.id.recoveryPlanRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecoveryPlanAdapter(recoveryPlanList, imageResources)
        { position ->
            if (position == 0) {
                val intent = Intent(this, InjuryTodayWorkoutActivity::class.java)
                startActivity(intent)
            }
        }

        val commonInjuriesList = listOf("Sprained Ankle", "Calf Strain", "Shoulder\nTendinitis")
//        val imageResources2 = listOf(
//            R.drawable.common_injuries_img1,
//            R.drawable.common_injuries_img2,
//            R.drawable.common_injuries_img3
//        )
//        val recyclerView2: RecyclerView = findViewById(R.id.commonInjuriesRecyclerView)
//        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView2.adapter = CommonInjuriesAdapter(commonInjuriesList, imageResources2)
    }
}