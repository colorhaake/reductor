package javatest.colorhaake.com.example3.view.main

import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import javatest.colorhaake.com.example3.R
import kotlinx.android.synthetic.main.item_search_bar.view.*

data class SearchBarViewInfo(val onTextChangeListener: (String) -> Unit)

class SearchBarDelegate
    : AbsListItemAdapterDelegate<Item<SearchBarViewInfo>, Item<*>, SearchBarDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(
            item: Item<SearchBarViewInfo>, viewHolder: ViewHolder, payloads: List<Any>
    ) = viewHolder.bind(item)

    override fun isForViewType(item: Item<*>, items: List<Item<*>>, position: Int) =
            item.getViewType() == ViewType.SEARCH_BAR

    class ViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_bar, parent, false)
    ) {
        fun bind(item: Item<SearchBarViewInfo>) {
            if (item.mData == null) return

            with(itemView) {
                edit_text.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) { }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (p0 == null) return

                        item.mData.onTextChangeListener(p0.toString())
                    }
                })
            }
        }
    }
}
