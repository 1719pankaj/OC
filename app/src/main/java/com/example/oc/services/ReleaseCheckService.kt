package com.example.oc.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.work.WorkerParameters
import com.example.oc.MainActivity
import com.example.oc.R
import com.example.oc.UpdateActivity
import com.example.oc.data.RnN
import com.example.oc.secrets.KeysAndSecrets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray

class ReleaseCheckWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val owner = "1719pankaj"
    private val repo = "OC"
    val notificationId = "update_notiff"



    override fun doWork(): Result {
        GlobalScope.launch(Dispatchers.IO) {

            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.github.com/repos/1719pankaj/OC/releases")
                .build()

            //wrap the next 2 lines in try/catch
           try {
               val response = client.newCall(request).execute()
               val releasesJson = JSONArray(response.body?.string())


               if (releasesJson.length() > 0) {
                   val latestRelease = releasesJson.getJSONObject(0)
                   val latestVersion = latestRelease.getString("tag_name")

                   if (latestVersion > RnN.CurrentVersion) {
                       showNotification(
                           "New Release Available",
                           "Latest version is now available.",
                           latestVersion
                       )
                   }
               }
           } catch (e: Exception) {
               Log.e("ReleaseCheckWorker", "Error checking for new release", e)
           }

        }
        return Result.success()
    }

    private fun showNotification(title: String, message: String, latestVersion: String) {
        val builder = NotificationCompat.Builder(applicationContext, "update_notiff")
            .setSmallIcon(R.drawable.ic_update_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val intent = Intent(applicationContext, UpdateActivity::class.java)
        intent.action = "latest_release$latestVersion"

        // Pass any data to the Fragment through the intent extras
        intent.putExtra("direct_download_flag", "dflg")

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            flags
        )

        builder.setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(1, builder.build())
        }
    }



}