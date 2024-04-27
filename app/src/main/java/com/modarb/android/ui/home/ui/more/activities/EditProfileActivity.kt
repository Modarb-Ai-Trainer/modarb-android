package com.modarb.android.ui.home.ui.more.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.modarb.android.R

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val genderButton: Button = findViewById(R.id.genderButton)
        val genders = arrayOf("Male", "Female")

        genderButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select Gender")
            val listView = ListView(this)
            listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genders)
            builder.setView(listView)

            val dialog = builder.create()
            dialog.show()

            listView.setOnItemClickListener { _, _, position, _ ->
                val selectedGender = genders[position]
                genderButton.text = selectedGender
                dialog.dismiss()
            }
        }


        val startButton: Button = findViewById(R.id.startButton)

        startButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}