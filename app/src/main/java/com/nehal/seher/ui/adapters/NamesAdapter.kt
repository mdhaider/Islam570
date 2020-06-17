package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.Names
import kotlinx.android.synthetic.main.layout_names_list_item.view.*

class NamesAdapter(private val names: ArrayList<Names>) :
    RecyclerView.Adapter<NamesAdapter.DataViewHolder>() {
    private var itemAction: ((Names) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(names: Names) {
            itemView.apply {
                tvName.text = names.name
                itemAction?.let {
                     itemView.setOnClickListener { it(names) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_names_list_item, parent, false)
        )

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(names[position])
    }

    fun setItemAction(action: (Names) -> Unit) {
        this.itemAction = action
    }

    fun addNames(names: List<Names>) {
        this.names.apply {
            clear()
            addAll(names)
        }
    }
}