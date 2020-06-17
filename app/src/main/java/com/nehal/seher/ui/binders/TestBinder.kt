package com.nehal.seher.ui.binders

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nehal.seher.R
import com.nehal.seher.model.Dua
import com.nehal.seher.ui.binders.TestBinder.DuaViewHolder
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class TestBinder : ItemBinder<Dua, DuaViewHolder>() {
   override fun createViewHolder(parent: ViewGroup): DuaViewHolder {
        return DuaViewHolder(inflate(parent,R.layout.layout_duas_list_item))
    }

   override fun canBindData(item: Any?): Boolean {
        return item is Dua
    }

   override fun bindViewHolder(holder: DuaViewHolder, item: Dua) {
        holder.tvCarName.text=item.name
    }

    inner class DuaViewHolder(itemView: View) :
        ItemViewHolder<Dua>(itemView) {
        var tvCarName: TextView = itemView.findViewById(R.id.tvNameDua)
    }
}