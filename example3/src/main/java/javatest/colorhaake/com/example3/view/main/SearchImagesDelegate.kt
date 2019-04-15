package javatest.colorhaake.com.example3.view.main

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import javatest.colorhaake.com.example3.R
import javatest.colorhaake.com.example3.extension.setSize
import javatest.colorhaake.com.example3.model.ImageData
import javatest.colorhaake.com.example3.utils.getScreenWidth
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_search_images.view.*

class SearchImagesDelegate
    : AbsListItemAdapterDelegate<Item<List<ImageData>>, Item<*>, SearchImagesDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(parent)

    override fun onBindViewHolder(
            item: Item<List<ImageData>>, viewHolder: ViewHolder, payloads: List<Any>
    ) = viewHolder.bind(item)

    override fun isForViewType(item: Item<*>, items: List<Item<*>>, position: Int) =
            item.getViewType() == ViewType.SEARCH_IMAGES

    class ViewHolder(val parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_search_images, parent, false)
    ) {
        fun bind(item: Item<List<ImageData>>) {
            if (item.mData == null) return

            with(itemView) {
                val size = getScreenWidth() / 3
                item.mData.map { createItemView(context, layout_images, it, size) }
                        .forEach(layout_images::addView)
            }
        }

        private fun createItemView(context: Context, root: ViewGroup, data: ImageData, size: Int): View =
                LayoutInflater.from(context).inflate(R.layout.item_image, root, false).apply {
                    image.setSize(size, size)
                    image.setImageURI(data.previewUrl)
                }
    }
}
