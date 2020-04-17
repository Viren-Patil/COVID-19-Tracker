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

class StateSubscriptionWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        Log.e("ENTERED_WORK", "Perfect.")

        val url = "https://api.covid19india.org/data.json"
        val stringRequest: StringRequest = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { response ->

                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("statewise")
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        if (Tools.check(applicationContext, data.getString("state"))) {
                            val state:String = data.getString("state")
                            val notificationId:Int = 121
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                            val pendingIntent = PendingIntent.getActivity(
                                    applicationContext, 0, intent, 0)
                            val builder = NotificationCompat.Builder(
                                    applicationContext,
                                    applicationContext.getString(R.string.channel_id))
                                    .setSmallIcon(R.mipmap.ic_launcher_round)
                                    .setContentTitle(state)
                                    .setContentText("Confirmed cases: " +
                                            "${Tools.fetch(
                                                    applicationContext, 
                                                    data.getString("state"), 
                                                    Tools.TYPE_CONFIRMED_CASES)} (+5 in 4h)")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                            val notificationManager =
                                    NotificationManagerCompat.from(applicationContext)
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