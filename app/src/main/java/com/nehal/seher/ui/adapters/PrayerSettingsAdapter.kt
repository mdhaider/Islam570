package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.PrayerTimesSettingsModel
import kotlinx.android.synthetic.main.layout_prayer_settings_list_item.view.*

class PrayerSettingsAdapter :
    RecyclerView.Adapter<PrayerSettingsAdapter.PrayerSettingsViewHolder>() {
    private var itemAction: ((PrayerTimesSettingsModel) -> Unit)? = null
    private var items: List<PrayerTimesSettingsModel> = ArrayList()

    inner class PrayerSettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PrayerTimesSettingsModel) {
            itemView.apply {
                itemView.apply {
                    tvTitle.text = item.title
                    if(!item.subTitle.isNullOrEmpty()){
                        tvSubTitle.text = item.subTitle
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
                .inflate(R.layout.layout_prayer_settings_list_item, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PrayerSettingsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItemAction(action: (PrayerTimesSettingsModel) -> Unit) {
        this.itemAction = action
    }

    fun submitList(itemList: List<PrayerTimesSettingsModel>) {
        items = itemList
    }

}