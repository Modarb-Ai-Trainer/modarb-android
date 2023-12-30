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

class ItemSelectionView(view: View, ctx: Context) : SelectedItemsAdapter.OnItemClickListener {

    private var itemList: List<ItemSelectionModel> = listOf(
        ItemSelectionModel("Barbells"),
        ItemSelectionModel("Dumbbells"),
        ItemSelectionModel("Gym machines"),
        ItemSelectionModel("Resistance band"),
        ItemSelectionModel("Body weight")
    )

    init {

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(ctx)
        recyclerView.layoutManager = layoutManager
        val adapter = SelectedItemsAdapter(itemList, this)
        recyclerView.adapter = adapter


        val questionTitle = view.findViewById<TextView>(R.id.questionTitle)
        questionTitle.text = ctx.getString(R.string.what_equipment_do_you_have)
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