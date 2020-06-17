package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.DuaModel
import kotlinx.android.synthetic.main.layout_duas_list_item.view.*

class DuaListAdapter(private val duas: ArrayList<DuaModel>) :
    RecyclerView.Adapter<DuaListAdapter.DataViewHolder>() {
    private var itemAction: ((DuaModel) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dua: DuaModel) {
            itemView.apply {
                tvNameDua.text = dua.dua_title
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

    fun setItemAction(action: (DuaModel) -> Unit) {
        this.itemAction = action
    }

    fun addDuasForCat(para: List<DuaModel>) {
        this.duas.apply {
            clear()
            addAll(para)
        }
    }
}