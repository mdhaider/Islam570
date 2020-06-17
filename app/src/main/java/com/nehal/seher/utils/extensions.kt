package com.nehal.seher.utils

import android.content.Context
import android.content.res.AssetManager
import android.os.Handler
import android.view.View
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.Gson
import com.nehal.seher.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}

fun AppCompatActivity.toolbar(toolbar: Toolbar, titleStr: String = "") {
    setSupportActionBar(toolbar)
    supportActionBar?.title = titleStr
    toolbar.apply {
        setNavigationIcon(R.drawable.ic_arrow_back_grey_24dp)
        setNavigationOnClickListener { finish() }
    }

    fun Context.loadJSONFromAssets(fileName: String): String {
        return applicationContext.assets.open(fileName).bufferedReader().use { reader ->
            reader.readText()
        }
    }
}
