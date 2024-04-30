package com.modarb.android.ui.menu.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.activities.MenuActivity
import com.modarb.android.ui.menu.Adapters.ActiveUsersAdapter
import com.modarb.android.ui.menu.models.User

class ChallengeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        val imageView = findViewById<View>(R.id.backButton)
        imageView.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
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
    }
}