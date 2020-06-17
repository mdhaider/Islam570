package com.nehal.seher.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nehal.seher.R
import com.nehal.seher.SeherApplication
import com.nehal.seher.model.QuraanList
import kotlinx.android.synthetic.main.layout_more_item.view.*

class QuraanAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<QuraanList> = ArrayList()
    private var itemAction: ((QuraanList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_more_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(quraanList: List<QuraanList>) {
        items = quraanList
    }

    fun setItemAction(action: (QuraanList) -> Unit) {
        this.itemAction = action
    }

    inner class ItemViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.imgMore
        private val title = itemView.tvMore
        private val new = itemView.tvNew

        fun bind(quraanList: QuraanList) {
            title.text = quraanList.title
            if(quraanList.isNew) new.visibility=View.VISIBLE else new.visibility=View.GONE

            image.run { setImageResource(quraanList.image) }

            Glide.with(SeherApplication.instance)
                .load(quraanList.image)
                .circleCrop()
                .into(image)
            itemAction?.let {
                itemView.setOnClickListener { it(quraanList) }
            }
        }
    }
}