package com.example.covid_19tracker.utilities

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.covid_19tracker.MainActivity
import com.example.covid_19tracker.R
import org.json.JSONObject
import java.util.*

class StateSubscriptionWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val currTime:Calendar = Calendar.getInstance();
        Log.e("COVID [${currTime.get(Calendar.HOUR_OF_DAY)}:${currTime.get(Calendar.MINUTE)}]", "Work initiated.")

        val url = "https://api.covid19india.org/data.json"
        val stringRequest: StringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { response ->

                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("statewise")

                    for (i in 0 until jsonArray.length()) {

                        val data = jsonArray.getJSONObject(i)
                        val state: String = data.getString("state")

                        if (Tools.check(applicationContext, state)) {

                            // required vars from prefs
                            val confirmedCases:Int = data.getString("confirmed").toInt()
                            val differenceInCases = confirmedCases - Tools.fetch(applicationContext, state, Tools.TYPE_CONFIRMED_CASES)!!.toInt()

                            if (differenceInCases == 0) continue;

                            val notificationId: Int = Tools.fetch(applicationContext, state, Tools.TYPE_NOTIFICATION_ID)!!.toInt()
                            val differenceInTime: Long = (Calendar.getInstance().timeInMillis - Tools.fetch(applicationContext, state, Tools.TYPE_LAST_TIMESTAMP)!!.toLong())/1000
                            val readableTimeDifference:String =
                                    if (differenceInTime/60 <= 0) differenceInTime.toString() + "s"
                                    else if (differenceInTime/60/60 <= 0) (differenceInTime/60).toString() + "m"
                                    else if (differenceInTime/60/60/24 <= 0) (differenceInTime/60/60).toString() + "h"
                                    else (differenceInTime/60/60/24).toString() + "d"

                            // building and showing notification
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
                            val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.channel_id))
                                    .setSmallIcon(R.mipmap.ic_launcher_round)
                                    .setContentTitle(state)
                                    .setContentText("Confirmed cases: $confirmedCases (" + (if (differenceInCases > 0) "+" else "") + "$differenceInCases in $readableTimeDifference)")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                            val notificationManager = NotificationManagerCompat.from(applicationContext)
                            notificationManager.notify(notificationId, builder.build())
                        }
                    }
                },
                Response.ErrorListener { error ->
                    Log.e("FAILED_STATE_SUBSCR", error.message!!)
                })
        Volley.newRequestQueue(applicationContext).add(stringRequest)

        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }
}
