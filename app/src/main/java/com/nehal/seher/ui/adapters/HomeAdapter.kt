package com.nehal.seher.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.QuraanList
import kotlinx.android.synthetic.main.layout_quraan_list_item.view.*


class HomeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<QuraanList> = ArrayList()
    private var itemAction: ((QuraanList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_quraan_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is BlogViewHolder -> {
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

    inner class BlogViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.quraan_list_image
        private val title = itemView.quraan_list_title
        private val subtitle = itemView.quraan_list_subtitle

        fun bind(quraanList: QuraanList) {
            title.text = quraanList.title
            image.run { setImageResource(quraanList.image) }

            itemAction?.let {
                itemView.setOnClickListener { it(quraanList) }
            }
        }
    }
}