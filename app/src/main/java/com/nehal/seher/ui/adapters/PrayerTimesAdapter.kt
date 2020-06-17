package com.nehal.seher.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nehal.seher.R
import com.nehal.seher.model.prayertimes.PrayerTimesData
import kotlinx.android.synthetic.main.layout_prayer_times_list_item.view.*

class PrayerTimesAdapter(private val names: ArrayList<PrayerTimesData>) :
    RecyclerView.Adapter<PrayerTimesAdapter.DataViewHolder>() {
    private var itemAction: ((PrayerTimesData) -> Unit)? = null

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(prayerTimesData: PrayerTimesData) {
            itemView.apply {
                tvDate.text = prayerTimesData.date.readable
                tvFajr.text = prayerTimesData.timings.fajr
                tvDhuhr.text =prayerTimesData.timings.dhuhr
                tvAsr.text = prayerTimesData.timings.asr
                tvMaghrib.text =prayerTimesData.timings.maghrib
                tvIsha.text = prayerTimesData.timings.isha
                itemAction?.let {
                    // itemView.setOnClickListener { it(para) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_prayer_times_list_item, parent, false)
        )

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(names[position])
    }

    fun setItemAction(action: (PrayerTimesData) -> Unit) {
        this.itemAction = action
    }

    fun addPrayerTimes(para: List<PrayerTimesData>) {
        this.names.apply {
            clear()
            addAll(para)
        }
    }
}