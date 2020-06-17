package com.nehal.seher.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nehal.seher.SeherApplication
import java.io.File
import java.io.IOException
import kotlin.reflect.KClass

object FileUtils {

    fun getPdfUrl(fileName: String): String {
        return "$fileName?raw=true"
    }

    fun getRootDirPath(context: Context): String {

        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {

            val file: File = ContextCompat.getExternalFilesDirs(

                context.applicationContext,

                null

            )[0]

            file.absolutePath

        } else {

            context.applicationContext.filesDir.absolutePath

        }

    }

     fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = SeherApplication.instance.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}