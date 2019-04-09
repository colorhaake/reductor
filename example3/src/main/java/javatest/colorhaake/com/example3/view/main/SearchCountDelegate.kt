package javatest.colorhaake.com.example3.view.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import javatest.colorhaake.com.example3.R
import kotlinx.android.synthetic.main.item_search_count.view.*

class SearchCountDelegate
    : AbsListItemAdapterDelegate<Item<Int>, Item<*>, SearchCountDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(
            item: Item<Int>, viewHolder: ViewHolder, payloads: List<Any>
    ) = viewHolder.bind(item)

    override fun isForViewType(item: Item<*>, items: List<Item<*>>, position: Int) =
            item.getViewType() == ViewType.SEARCH_COUNT

    class ViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_count, parent, false)
    ) {
        fun bind(item: Item<Int>) {
            if (item.mData == null) return
            
            with(itemView) {
                text_count.text = item.mData.toString()
            }
        }
    }
}
