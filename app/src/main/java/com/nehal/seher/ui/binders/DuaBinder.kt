package com.nehal.seher.ui.binders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nehal.seher.R
import com.nehal.seher.SeherApplication
import com.nehal.seher.model.Dua
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class DuaBinder :
    ItemBinder<Dua, DuaBinder.HeaderViewHolder>() {
    private var itemAction: ((Dua, String) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(inflate(parent, R.layout.layout_dua_home))
    }

    override fun canBindData(item: Any?): Boolean {
        return item is Dua
    }

    override fun bindViewHolder(holder: HeaderViewHolder, item: Dua) {
        holder.tvTitle.text = item.name
        holder.tvArDua.text = item.duaAr
        holder.tvTrans.text = item.transLiteration
        holder.tvMean.text = item.meaning
        holder.tvSource.text = item.source

        Glide.with(SeherApplication.instance)
            .load(R.drawable.img3)  // .load("${item.imgUrl}?raw=true")
            .fitCenter()
            .circleCrop()
            .into(holder.imgPoster)


        itemAction?.let {
            holder.tvMore.setOnClickListener {
                it(item,"more")
            }
            holder.duaContainer.setOnClickListener {
                it(item,"container")
            }
        }
    }

    override fun getSpanSize(maxSpanCount: Int): Int {
        return maxSpanCount
    }

    inner class HeaderViewHolder(itemView: View) :
        ItemViewHolder<Dua>(itemView) {
        var imgPoster: ImageView = itemView.findViewById(R.id.imgpo)
        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvMore: TextView = itemView.findViewById(R.id.tvMore)
        var tvArDua: TextView = itemView.findViewById(R.id.tvArDua)
        var tvTrans: TextView = itemView.findViewById(R.id.tvTrans)
        var tvMean: TextView = itemView.findViewById(R.id.tvMean)
        var tvSource: TextView = itemView.findViewById(R.id.tvSource)
        var duaContainer: View = itemView.findViewById(R.id.duaConatiner)
    }

    fun setItemAction(action: (Dua, String) -> Unit) {
        this.itemAction = action
    }
}