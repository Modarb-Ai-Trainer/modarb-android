package com.modarb.android.ui.onboarding.ViewPagerViews

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.adapters.SelectedItemsAdapter
import com.modarb.android.ui.onboarding.models.ItemSelectionModel
import kotlin.math.abs

class ItemSelectionView(
    view: View,
    ctx: Context,
    private var itemList: List<ItemSelectionModel>,
    var title: Int,
    var type: String
) : SelectedItemsAdapter.OnItemClickListener {

    init {

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(ctx)
        recyclerView.layoutManager = layoutManager
        val adapter = SelectedItemsAdapter(itemList, this, type)
        recyclerView.adapter = adapter


        val questionTitle = view.findViewById<TextView>(R.id.questionTitle)
        questionTitle.text = ctx.getString(title)
    }

    /*
      @param position
       negative position -> remove
       positive position -> add
     */
    override fun onItemClick(position: Int) {
        this.itemList[abs(position) - 1].isSelected = !itemList[abs(position) - 1].isSelected
    }
}