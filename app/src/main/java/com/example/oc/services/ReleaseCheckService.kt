package com.example.oc.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.work.WorkerParameters
import com.example.oc.MainActivity
import com.example.oc.R
import com.example.oc.data.RnN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.File

class ReleaseCheckWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val owner = "1719pankaj"
    private val repo = "OC"
    private val token = "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"
    val notificationId = "update_notiff"



    override fun doWork(): Result {
        GlobalScope.launch(Dispatchers.IO) {

            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.github.com/repos/$owner/$repo/releases")
                .addHeader("Authorization", "token $token")
                .build()

            //wrap the next 2 lines in try/catch
           try {
               val response = client.newCall(request).execute()
               val releasesJson = JSONArray(response.body?.string())


               if (releasesJson.length() > 0) {
                   val latestRelease = releasesJson.getJSONObject(0)
                   val latestVersion = latestRelease.getString("tag_name")

                   clearAppDataDir(latestVersion)

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

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = "latest_release$latestVersion"

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

    fun clearAppDataDir(currentVersion: String) {
        val appDataDir = applicationContext.getExternalFilesDir("downloads")

        if (appDataDir != null) {
            val files = appDataDir.listFiles()
            if (files != null) {
                val versionCodePattern = """^$currentVersion(-\d+)?\.apk$""".toRegex()
                val currentVersionFiles = mutableSetOf<String>()

                // Identify current version files and older files
                val filesToDelete = mutableListOf<File>()
                for (file in files) {
                    if (file.isFile) {
                        val fileName = file.name
                        if (fileName.matches(versionCodePattern)) {
                            if (fileName == "$currentVersion.apk") {
                                currentVersionFiles.add(fileName)
                            } else if (fileName.startsWith("$currentVersion-")) {
                                val versionNumber = fileName.substring(currentVersion.length + 1, fileName.lastIndexOf('.'))
                                if (versionNumber.toIntOrNull() != null) {
                                    filesToDelete.add(file)
                                }
                            }
                        }
                    }
                }

                // Delete older files and duplicates of current version
                for (file in filesToDelete) {
                    file.deleteRecursively()
                }

                // Delete duplicate current version files
                currentVersionFiles.forEach { fileName ->
                    files.filter { it.name == fileName && it != filesToDelete }
                        .forEach { it.delete() }
                }
            }
        }
    }

}