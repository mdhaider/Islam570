package com.nehal.seher.repository

import com.nehal.seher.R
import com.nehal.seher.model.QuraanList

class QuraanListRepository{

    companion object{

        fun createDataSet(): ArrayList<QuraanList>{
            val list = ArrayList<QuraanList>()
            list.add(
                QuraanList(1,
                    "Quran (Surah)",
                    R.drawable.img1,
                    false
                )
            )
            list.add(
                QuraanList(2,
                    "Quran (Para)",
                    R.drawable.img2,true
                )
            )
            return list
        }
    }
}