package com.modarb.android.ui.home.ui.more.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.activities.ReminderActivity
import com.modarb.android.ui.home.ui.more.activities.SettingActivity
import com.modarb.android.ui.onboarding.activities.SplashActivity
import com.modarb.android.ui.onboarding.utils.UserPref.UserPrefUtil

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO refactor to binding
        val cardView4 = view.findViewById<View>(R.id.cardView5)
        cardView4.setOnClickListener {
            startActivity(Intent(requireContext(), ReminderActivity::class.java))
        }
        val cardView5 = view.findViewById<View>(R.id.cardView6)
        cardView5.setOnClickListener {
            startActivity(Intent(requireContext(), SettingActivity::class.java))
        }

        val logout = view.findViewById<View>(R.id.cardView8)
        logout.setOnClickListener {
            UserPrefUtil.saveUserData(requireContext(), null)
            UserPrefUtil.setUserLoggedIn(requireContext(), false)
            startActivity(Intent(requireContext(), SplashActivity::class.java))
            requireActivity().finish()
        }
    }
}
