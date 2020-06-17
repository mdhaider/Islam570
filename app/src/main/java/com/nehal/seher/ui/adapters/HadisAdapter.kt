package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.hadith.HadithCollection
import kotlinx.android.synthetic.main.layout_hadis_list_item.view.*


class HadisAdapter(private val hadis: ArrayList<HadithCollection>) :
    RecyclerView.Adapter<HadisAdapter.DataViewHolder>() {
    private var itemAction: ((HadithCollection) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hadis: HadithCollection) {
            itemView.apply {
                surahName.text = hadis.nameEn
              //  surahNameArabic.text = hadis.subTitle
                itemAction?.let {
                     itemView.setOnClickListener { it(hadis) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_hadis_list_item, parent, false)
        )

    override fun getItemCount(): Int = hadis.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(hadis[position])
    }

    fun setItemAction(action: (HadithCollection) -> Unit) {
        this.itemAction = action
    }

    fun addHadis(hadis: List<HadithCollection>) {
        this.hadis.apply {
            clear()
            addAll(hadis)
        }
    }
}