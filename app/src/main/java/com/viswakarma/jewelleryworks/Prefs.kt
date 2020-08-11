package com.viswakarma.jewelleryworks

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {


//    lateinit var loggedInUser: LoggedInUser
    private val PREFS_FILENAME: String = context.packageName

//    val BACKGROUND_COLOR = "introduction_screen"
    val INTRODUCTION_SCREEN = "introduction_screen"
    val DASHBOARD_ACCESS_PIN = "dashboardaccesspin"
    val REFRESH_TOKEN = "refreshToken"
    val TOKEN = "token"
    val LOGGED_IN_USER = "LOGGED_IN_USER"
    val IS_FETCH_PREVIOUS_DATA_COMPLETED = "IS_FETCH_PREVIOUS_DATA_COMPLETED"
    val TODAYS_QUESTIONAIRE = "TODAYS_QUESTIONAIRE"


    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
/*
    var bgColor: Int
        get() = prefs.getInt(BACKGROUND_COLOR, Color.BLACK)
        set(value) = prefs.edit().putInt(BACKGROUND_COLOR, value).apply()*/

    var isIntroductionScreenCompleted: Boolean
        get() = prefs.getBoolean(INTRODUCTION_SCREEN, false)
        set(value) = prefs.edit().putBoolean(INTRODUCTION_SCREEN, value).apply()

    var dashboardAccessPin: String?
        get() = prefs.getString(DASHBOARD_ACCESS_PIN, "")
        set(value) = prefs.edit().putString(DASHBOARD_ACCESS_PIN, value).apply()

    var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, "")
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    var token: String?
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var todaysQuestionnaire: String?
        get() = prefs.getString(TODAYS_QUESTIONAIRE, "")
        set(value) = prefs.edit().putString(TODAYS_QUESTIONAIRE, value).apply()

    var isFetchDataCompleted: Boolean
            get() = prefs.getBoolean(IS_FETCH_PREVIOUS_DATA_COMPLETED, false)
            set(value) = prefs.edit().putBoolean(IS_FETCH_PREVIOUS_DATA_COMPLETED, value).apply()






}