package com.example.covid_19tracker.utilities

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject
import java.util.*

class Tools {

    companion object {
        val TYPE_CONFIRMED_CASES: Int = 0
        val TYPE_LAST_TIMESTAMP: Int = 1
        val TYPE_NOTIFICATION_ID: Int = 2

        fun check(context: Context, state: String): Boolean {
            if (context.getSharedPreferences("state_subscription", Context.MODE_PRIVATE)
                    .getString(state, null) == null) return false
            return true
        }

        fun fetch(context: Context, state: String, type: Int): String? {
            val stateStuff: String? = context
                    .getSharedPreferences("state_subscription", Context.MODE_PRIVATE)
                    .getString(state, null)
            if (type == TYPE_LAST_TIMESTAMP)
                return JSONObject(stateStuff!!).get("last_timestamp").toString()
            else if (type == TYPE_CONFIRMED_CASES)
                return JSONObject(stateStuff!!).get("confirmed_cases").toString()
            else
                return JSONObject(stateStuff!!).get("notification_id").toString()
        }

        fun cache(context: Context, state: String, confirmedCases: Int, notificationId: Int?) {
            val preferences: SharedPreferences = context
                    .getSharedPreferences("state_subscription", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            // building timestamp
            val calendar: Calendar = Calendar.getInstance()
//            val timestamp: String = "" +
//                    calendar.get(Calendar.DAY_OF_MONTH) + ":" +
//                    calendar.get(Calendar.MONTH) + ":" +
//                    calendar.get(Calendar.YEAR) + ":" +
//                    calendar.get(Calendar.HOUR_OF_DAY) + ":" +
//                    calendar.get(Calendar.MINUTE) + ":"
            // building json
            val stateStuff: JSONObject = JSONObject()
            stateStuff.put("confirmed_cases", confirmedCases)
            stateStuff.put("last_timestamp", calendar.timeInMillis)
            stateStuff.put("notification_id", notificationId
                    ?: fetch(context, state, TYPE_NOTIFICATION_ID))
            // committing to cache
            editor.putString(state, stateStuff.toString()).apply()
        }
    }

}