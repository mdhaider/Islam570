package com.nehal.seher.ui.binders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nehal.seher.R
import com.nehal.seher.SeherApplication
import com.nehal.seher.model.Poster
import com.nehal.seher.model.gregtohijri.HijriDateResponseData
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class DateNowBinder :
    ItemBinder<HijriDateResponseData, DateNowBinder.HeaderViewHolder>() {
    private var itemAction: ((HijriDateResponseData) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(inflate(parent, R.layout.layout_date_now_home))
    }

    override fun canBindData(item: Any?): Boolean {
        return item is HijriDateResponseData
    }

    override fun bindViewHolder(holder: HeaderViewHolder, item: HijriDateResponseData) {

        holder.tvHjDay.text= item.hijri?.day.toString()
        holder.tvHjDate.text= "${item.hijri?.month?.en}, ${item.hijri?.year}"
        holder.tvGrgDate.text=
            "${item.gregorian?.weekday?.en}, ${item.gregorian?.month?.en} ${item.gregorian?.day} ${item.gregorian?.year}"
        Glide.with(SeherApplication.instance)
            .load(R.drawable.img7)  // .load("${item.imgUrl}?raw=true")
            .fitCenter()
            .into(holder.imgBg)

        itemAction?.let {
            holder.imgEdit.setOnClickListener {
                it(item)
            }
        }

    }

    override fun getSpanSize(maxSpanCount: Int): Int {
        return maxSpanCount
    }

    inner class HeaderViewHolder(itemView: View) :
        ItemViewHolder<HijriDateResponseData>(itemView) {
        var tvHjDay: TextView = itemView.findViewById(R.id.hjDay)
        var tvHjDate: TextView = itemView.findViewById(R.id.hjDate)
        var tvGrgDate: TextView = itemView.findViewById(R.id.grgDate)
        var imgBg: ImageView = itemView.findViewById(R.id.imgBg)
        var imgEdit: ImageView = itemView.findViewById(R.id.imgEdit)
    }

    fun setItemAction(action: (HijriDateResponseData) -> Unit) {
        this.itemAction = action
    }
}