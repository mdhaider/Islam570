package com.nehal.seher.ui.binders

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nehal.seher.R
import com.nehal.seher.SeherApplication
import com.nehal.seher.model.Poster
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class PosterBinder :
    ItemBinder<Poster, PosterBinder.HeaderViewHolder>() {
    private var itemAction: ((Poster, String) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(inflate(parent, R.layout.layout_poster_home))
    }

    override fun canBindData(item: Any?): Boolean {
        return item is Poster
    }

    override fun bindViewHolder(holder: HeaderViewHolder, item: Poster) {
        Glide.with(SeherApplication.instance)
            .load(item.imgUrl)  // .load("${item.imgUrl}?raw=true")
            .fitCenter()
            .into(holder.imgPoster)

        Glide.with(SeherApplication.instance)
            .load(R.drawable.img3)  // .load("${item.imgUrl}?raw=true")
            .fitCenter()
            .circleCrop()
            .into(holder.imgPosterTop)


        itemAction?.let {
            holder.tvmore.setOnClickListener {
                it(item, "more")
            }
            holder.imgPoster.setOnClickListener {
                it(item, "poster")
            }
        }

    }

    override fun getSpanSize(maxSpanCount: Int): Int {
        return maxSpanCount
    }

    inner class HeaderViewHolder(itemView: View) :
        ItemViewHolder<Poster>(itemView) {
        var imgPoster: ImageView = itemView.findViewById(R.id.imgPoster)
        var imgPosterTop: ImageView = itemView.findViewById(R.id.imgpo)
        var tvmore: TextView = itemView.findViewById(R.id.tvMore)
    }

    fun setItemAction(action: (Poster, String) -> Unit) {
        this.itemAction = action
    }
}