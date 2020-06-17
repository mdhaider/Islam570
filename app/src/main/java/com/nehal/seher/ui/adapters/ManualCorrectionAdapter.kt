package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.ManualCorrectionModel
import com.nehal.seher.model.PrayerTimesSettingsModel
import kotlinx.android.synthetic.main.layout_prayer_settings_list_item.view.*

class ManualCorrectionAdapter :
    RecyclerView.Adapter<ManualCorrectionAdapter.PrayerSettingsViewHolder>() {
    private var itemAction: ((ManualCorrectionModel) -> Unit)? = null
    private var items: List<ManualCorrectionModel> = ArrayList()

    inner class PrayerSettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ManualCorrectionModel) {
            itemView.apply {
                itemView.apply {
                    tvTitle.text = item.namaz
                    if(!item.correction.isNullOrEmpty()){
                        tvSubTitle.text = item.correction
                        tvSubTitle.visibility=View.VISIBLE
                    }else{
                        tvSubTitle.visibility=View.GONE
                    }

                    itemAction?.let {
                        itemView.setOnClickListener { it(item) }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerSettingsViewHolder =
        PrayerSettingsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_manual_corr_item, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PrayerSettingsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItemAction(action: (ManualCorrectionModel) -> Unit) {
        this.itemAction = action
    }

    fun submitList(itemList: List<ManualCorrectionModel>) {
        items = itemList
    }

}