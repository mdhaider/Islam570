package com.nehal.seher.repository

class QuraanParaListRepository {

    companion object {
        fun createDataSet(): ArrayList<String> {
            val list = ArrayList<String>()
            for (i in 1..30) {
                list.add("Para $i")
            }

            return list
        }
    }
}