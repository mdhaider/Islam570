package com.nehal.seher.ui.binders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nehal.seher.R
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class HeaderBinder : ItemBinder<Header, HeaderBinder.HeaderViewHolder>() {
   override fun createViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(inflate(parent,R.layout.layout_header))
    }

   override fun canBindData(item: Any?): Boolean {
        return item is Header
    }

   override fun bindViewHolder(holder: HeaderViewHolder, item: Header) {
        holder.tvCarName.text=item.test
    }

    override fun getSpanSize(maxSpanCount: Int): Int {
        return maxSpanCount
    }

    inner class HeaderViewHolder(itemView: View) :
        ItemViewHolder<Header>(itemView) {
        var tvCarName: TextView = itemView.findViewById(R.id.tvHeader)
    }
}