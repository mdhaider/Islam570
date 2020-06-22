package com.nehal.seher.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "InsanePref"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val PHONE_NUM = Pair("phone_num", "")
    private val IS_ADMIN=Pair("is_admin",false)
    private val IS_PERMISSION_GRANTED=Pair("is_permission_granted",false)
    private val IS_WAITING=Pair("is_waiting", false)
    private val QIBLA_DERAJAT=Pair("qibla_derajat", 0.0f)
    private val USER_ID=Pair("userId", "")
    private val SIGNUP_STATE=Pair("state", 0)
    private val TODAYS_DATE = Pair("today_date",0L)
    private val IS_FIRST_TIME_INSTALL = Pair("first_time_install", true)
    private val IS_PHONE_NUMBER_VERIFIED = Pair("phone_verified", false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var phone: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(PHONE_NUM.first, PHONE_NUM.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(PHONE_NUM.first, value)
        }

    var isAdmin: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_ADMIN.first, IS_ADMIN.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_ADMIN.first, value)
        }

    var isFirstTimeInstall: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_FIRST_TIME_INSTALL.first, IS_FIRST_TIME_INSTALL.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_TIME_INSTALL.first, value)
        }

    var isWaitingForApproval: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_WAITING.first, IS_WAITING.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_WAITING.first, value)
        }

    var isPermissionGranted: Boolean
        get() = preferences.getBoolean(IS_PERMISSION_GRANTED.first, IS_PERMISSION_GRANTED.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_PERMISSION_GRANTED.first, value)
        }

    var isPhoneNumberVerified: Boolean
        get() = preferences.getBoolean(IS_PHONE_NUMBER_VERIFIED.first, IS_PHONE_NUMBER_VERIFIED.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_PHONE_NUMBER_VERIFIED.first, value)
        }


    var userId: String?
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getString(USER_ID.first, USER_ID.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putString(USER_ID.first, value)
        }

    var todaysDate: Long
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getLong(TODAYS_DATE.first, TODAYS_DATE.second)

        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putLong(TODAYS_DATE.first, value)
        }


    var signUpState: Int
        get() = preferences.getInt(SIGNUP_STATE.first, SIGNUP_STATE.second)

        set(value) = preferences.edit {
            it.putInt(SIGNUP_STATE.first, value)
        }

    var qiblaDerajat: Float
        get() = preferences.getFloat(QIBLA_DERAJAT.first, QIBLA_DERAJAT.second)
        set(value) = preferences.edit {
            it.putFloat(QIBLA_DERAJAT.first, value)
        }
}