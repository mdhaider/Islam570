package com.nehal.seher.repository

import com.nehal.seher.model.PrayerTimesSettingsModel

class PrayerTimesSetingRepository {

    companion object {

        fun createDataSet(): ArrayList<PrayerTimesSettingsModel> {
            val list = ArrayList<PrayerTimesSettingsModel>()

            list.add(PrayerTimesSettingsModel(1, "Location", "Bangalore"))
            list.add(PrayerTimesSettingsModel(2, "Prayer Times Conventions", ""))
            list.add(PrayerTimesSettingsModel(3, "Manual Correction", ""))
            list.add(PrayerTimesSettingsModel(4, "Asr Time Calculation", ""))
            list.add(PrayerTimesSettingsModel(5, "High Altitude Adjustment", ""))
            list.add(PrayerTimesSettingsModel(6, "Daylight Saving Time", ""))
            list.add(PrayerTimesSettingsModel(7, "Reset Settings", ""))
            return list
        }
    }
}