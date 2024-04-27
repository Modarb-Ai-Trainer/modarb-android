package com.modarb.android.ui.home.ui.more.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.modarb.android.R
import com.modarb.android.ui.home.ui.more.activities.ProfileActivity

class MoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView = view.findViewById<View>(R.id.cardView)
        cardView.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }
    }
}
