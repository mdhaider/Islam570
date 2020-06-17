package com.nehal.seher.repository

import com.nehal.seher.model.ManualCorrectionModel

class ManualCorrectionRepository {

    companion object {

        fun createDataSet(): ArrayList<ManualCorrectionModel> {
            val list = ArrayList<ManualCorrectionModel>()

            list.add(ManualCorrectionModel(1, "Fajar", "0 minutes"))
            list.add(ManualCorrectionModel(2, "Dhuhr", "0 minutes"))
            list.add(ManualCorrectionModel(3, "Asr", "0 minutes"))
            list.add(ManualCorrectionModel(4, "Maghrib", "0 minutes"))
            list.add(ManualCorrectionModel(5, "Isha", "0 minutes"))
            return list
        }
    }
}