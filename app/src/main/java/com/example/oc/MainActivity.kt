package com.example.oc

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.oc.databinding.ActivityMainBinding
import com.example.oc.services.ReleaseCheckWorker
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createNotificationChannel()

        if (intent.action?.startsWith("latest_release") == true) {
            // Initiate the download here
            val version = intent.action!!.subSequence(14, intent.action!!.length)
            val downloadUrl = "https://github.com/1719pankaj/OC/releases/download/$version/$version.apk"
            val title = "OC"
            val description = "Downloading Update"
            downloadWithDownloadManager(this, downloadUrl, title, description, version.toString())
        } else {
            try {
                val workRequest = OneTimeWorkRequest.Builder(ReleaseCheckWorker::class.java).build()
                WorkManager.getInstance(this).enqueue(workRequest)
            } catch (e: Exception) {
                Log.e("ReleaseCheckRunner", "Error: ${e.message}")
                Toast.makeText(this, "Network not available\nCan't check for updates.", Toast.LENGTH_SHORT).show()
            }
        }
    }





    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("update_notiff", "Update Notification", NotificationManager.IMPORTANCE_HIGH).apply {
                description = "Channel Description"
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun downloadWithDownloadManager(context: Context, url: String, title: String, description: String, version: String) {
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(title)
            .setDescription(description)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        // Get the app's private data directory
        val appDataDir = context.getExternalFilesDir(null)

        val downloadsDir = File(appDataDir, "downloads")

        if (!downloadsDir.exists())
            downloadsDir.mkdirs()

        val fileName = "$version.apk"
        val destinationFile = File(downloadsDir, fileName)
        request.setDestinationUri(Uri.fromFile(destinationFile))
        downloadManager.enqueue(request)
    }

}