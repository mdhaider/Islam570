package com.nehal.seher.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nehal.seher.R
import com.nehal.seher.SeherApplication
import com.nehal.seher.model.More
import kotlinx.android.synthetic.main.layout_more_item.view.*

class MoreAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var moreItems: List<More> = ArrayList()
    private var itemAction: ((More) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_more_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(moreItems[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return moreItems.size
    }

    fun submitList(moreList: List<More>) {
        moreItems = moreList
    }

    fun setItemAction(action: (More) -> Unit) {
        this.itemAction = action
    }

    inner class ItemViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.imgMore
        private val title = itemView.tvMore
        private val new = itemView.tvNew

        fun bind(more: More) {
            title.text = more.title

            if(more.isNew) new.visibility=View.VISIBLE else new.visibility=View.GONE

            Glide.with(SeherApplication.instance)
                .load(more.image)
                .circleCrop()
                .into(image)
            itemAction?.let {
                itemView.setOnClickListener { it(more) }
            }
        }
    }
}