package com.modarb.android.ui.home.ui.more.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.adapters.ActiveUsersAdapter
import com.modarb.android.ui.home.ui.more.adapters.ChallengeAdapter
import com.modarb.android.ui.home.ui.more.models.ChallengeModel
import com.modarb.android.ui.home.ui.more.models.User

class ChallengeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)


        // TODO refactor
        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            finish()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userList = listOf(
            User("Mark", "Challenge", R.drawable.ic_ranked_user1),
            User("adam", "Challenge", R.drawable.ic_ranked_user2),
            User("Jala", "Challenge", R.drawable.ic_ranked_user3),
            User("nardine", "Challenge", R.drawable.ic_ranked_user4)
        )

        val adapter = ActiveUsersAdapter(userList)
        recyclerView.adapter = adapter


        val recyclerView2: RecyclerView = findViewById(R.id.availableChallengesRecyclerView)
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val challengeList = listOf(
            ChallengeModel("Plank\nChallenge", R.drawable.rounded_gradient_rectangle2, "Join"),
            ChallengeModel("Push-Up Power\nChallenge", R.drawable.rounded_gradient_rectangle3, "Join"),
            ChallengeModel("Flex Friday Photo\nChallenge", R.drawable.rounded_gradient_rectangle4, "Join"),
            ChallengeModel("Squat Squad\nChallenge", R.drawable.rounded_gradient_rectangle5, "Join")
        )

        recyclerView2.adapter = ChallengeAdapter(challengeList)

    }
}