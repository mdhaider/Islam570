package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import kotlinx.android.synthetic.main.layout_duas_list_item.view.*

class DuaCategoryAdapter(private val duas: ArrayList<String>) :
    RecyclerView.Adapter<DuaCategoryAdapter.DataViewHolder>() {
    private var itemAction: ((String) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dua: String) {
            itemView.apply {
                tvNameDua.text = dua
                itemAction?.let {
                    itemView.setOnClickListener { it(dua) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_duas_list_item, parent, false)
        )

    override fun getItemCount(): Int = duas.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(duas[position])
    }

    fun setItemAction(action: (String) -> Unit) {
        this.itemAction = action
    }

    fun addDuas(para: List<String>) {
        this.duas.apply {
            clear()
            addAll(para)
        }
    }
}