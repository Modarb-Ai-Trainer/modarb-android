import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.modarb.android.R
import com.modarb.android.ui.onboarding.adapters.SelectedItemsAdapter

class ItemSelectionView(
    view: View,
    ctx: Context,
    var title: Int,
    type: String,
) {

    init {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(ctx)
        recyclerView.layoutManager = layoutManager

        val adapter = SelectedItemsAdapter(type)
        recyclerView.adapter = adapter

        val questionTitle = view.findViewById<TextView>(R.id.questionTitle)
        questionTitle.text = ctx.getString(title)

    }

    /*
      @param position
       negative position -> remove
       positive position -> add
     */
}
