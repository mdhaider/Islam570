package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R

import com.nehal.seher.model.Surah
import kotlinx.android.synthetic.main.layout_quraan_paara_list_item.view.*

class QuraanSurahAdapter(private val surah: ArrayList<Surah>) : RecyclerView.Adapter<QuraanSurahAdapter.DataViewHolder>() {
    private var itemAction: ((Surah, String) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(surah: Surah) {
            itemView.apply {
                surahName.text = "${surah.id}. ${surah.nameEn} (${surah.nameAr})"
            }

            itemAction?.let {
                //  itemView.setOnClickListener { it(para) }
                itemView.btnRead.setOnClickListener {
                    it(surah, "read")
                }
                itemView.btnListen.setOnClickListener {
                    it(surah, "listen")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_quraan_paara_list_item, parent, false)
        )

    override fun getItemCount(): Int = surah.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(surah[position])
    }

    fun setItemAction(action: (Surah, String) -> Unit) {
        this.itemAction = action
    }

    fun addSurahs(surah: List<Surah>) {
        this.surah.apply {
            clear()
            addAll(surah)
        }
    }
}