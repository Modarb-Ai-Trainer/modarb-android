package com.modarb.android.ui.onboarding.ViewPagerViews

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.adapters.MessagesAdapter

class MessageView(view: View, ctx: Context, data: List<String>) {
    init {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(ctx)
        recyclerView.layoutManager = layoutManager
        val adapter = MessagesAdapter(ctx, data)
        recyclerView.adapter = adapter

    }
}