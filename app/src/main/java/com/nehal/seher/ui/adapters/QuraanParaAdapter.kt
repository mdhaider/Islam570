package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.Para
import com.nehal.seher.ui.adapters.QuraanParaAdapter.DataViewHolder
import com.nehal.seher.model.Surah
import kotlinx.android.synthetic.main.layout_quraan_paara_list_item.view.*

class QuraanParaAdapter(private val para: ArrayList<Para>) : RecyclerView.Adapter<DataViewHolder>() {
    private var itemAction: ((Para,String) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(para: Para) {
            itemView.apply {
                surahName.text = "${para.id}. ${para.nameEn} (${para.nameAr})"
            }

            itemAction?.let {
                //  itemView.setOnClickListener { it(para) }
                itemView.btnRead.setOnClickListener {
                    it(para, "read")
                }
                itemView.btnListen.setOnClickListener {
                    it(para, "listen")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_quraan_paara_list_item, parent, false)
        )

    override fun getItemCount(): Int = para.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(para[position])
    }

    fun setItemAction(action: (Para,String) -> Unit) {
        this.itemAction = action
    }

    fun addParas(para: List<Para>) {
        this.para.apply {
            clear()
            addAll(para)
        }
    }
}