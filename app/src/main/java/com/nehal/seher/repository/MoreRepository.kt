package com.nehal.seher.repository

import com.nehal.seher.R
import com.nehal.seher.model.More

class MoreRepository {

    companion object {

        fun createMoreItems(): ArrayList<More> {
            val list = ArrayList<More>()
            list.add(
                More(
                    1,
                    "Allah Names",
                    R.drawable.download,
                    true
                )
            )
            list.add(
                More(
                    2,
                    "Daily Duas",
                    R.drawable.dua_hands,
                    false
                )
            )
            list.add(
                More(
                    3,
                    "Qibla",
                    R.drawable.qibla_img,
                    false
                )
            )

            list.add(
                More(
                    4,
                    "Hadith",
                    R.drawable.img7,
                    false
                )
            )

            return list
        }
    }
}