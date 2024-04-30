package com.modarb.android.ui.home.ui.more.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.activities.ProfileActivity
import com.modarb.android.ui.home.ui.more.activities.ReminderActivity
import com.modarb.android.ui.home.ui.more.activities.SettingActivity
import com.modarb.android.ui.menu.activities.ChallengeActivity

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView = view.findViewById<View>(R.id.cardView)
        cardView.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }


        val cardView2 = view.findViewById<View>(R.id.cardView4)
        cardView2.setOnClickListener {
            startActivity(Intent(requireContext(), ChallengeActivity::class.java))
        }
        val cardView3 = view.findViewById<View>(R.id.cardView5)
        cardView3.setOnClickListener {
            startActivity(Intent(requireContext(), ReminderActivity::class.java))
        }
        val cardView4 = view.findViewById<View>(R.id.cardView6)
        cardView4.setOnClickListener {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }
    }
}
