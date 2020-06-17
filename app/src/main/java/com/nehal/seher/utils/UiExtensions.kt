package com.nehal.seher.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import timber.log.Timber

@Suppress("DEPRECATION")
fun Context.isServiceRunning(serviceClassName: String): Boolean {
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager

    return activityManager?.getRunningServices(Integer.MAX_VALUE)?.any { it.service.className == serviceClassName } ?: false
}

fun Context.dpToPx(valueInDp: Float): Float {
    val metrics = resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
}

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    currentFocus?.let {
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

val Fragment.supportActionBar
    get() = (activity as? AppCompatActivity)?.supportActionBar

fun Fragment.openUrl(url: String): Boolean {
    try {
        val validUrl = if (!url.startsWith("www.") && !url.startsWith("http://") && !url.startsWith("https://")) {
            "http://www.$url"
        } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
            "http://$url"
        } else {
            url
        }

        return true
    } catch (e: Exception) {
        Timber.e(e)

        return false
    }
}

fun NavController.setupActionBar(activity: AppCompatActivity, appBarConfig: AppBarConfiguration) =
        activity.setupActionBarWithNavController(this, appBarConfig)