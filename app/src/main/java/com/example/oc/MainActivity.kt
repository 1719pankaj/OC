package com.example.oc

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.oc.data.RnN
import com.example.oc.databinding.ActivityMainBinding
import com.example.oc.fragment.MyCallback
import com.example.oc.fragment.UpdateFragment
import com.example.oc.services.ReleaseCheckWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File


class MainActivity : AppCompatActivity(), MyCallback {

    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        sharedPreferences = getSharedPreferences("OcPrefs", Context.MODE_PRIVATE)
        getSharedPrefs()

        createNotificationChannel()

        deleteObsoletePackages()


        try {
            val workRequest = OneTimeWorkRequest.Builder(ReleaseCheckWorker::class.java).build()
            WorkManager.getInstance(this).enqueue(workRequest)
        } catch (e: Exception) {
            Log.e("ReleaseCheckRunner", "Error: ${e.message}")
            Toast.makeText(this, "Network not available\nCan't check for updates.", Toast.LENGTH_SHORT).show()
        }
    }


    fun getSharedPrefs() {
        sharedPreferences = getSharedPreferences("OcPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences

        RnN.OldBasic = editor.getDouble("OldBasic", RnN.OldBasic)
        RnN.OldDA = editor.getDouble("OldDA", RnN.OldDA)
        RnN.OldHRA = editor.getDouble("OldHRA", RnN.OldHRA)
        RnN.Days = editor.getInt("Days", RnN.Days)
        RnN.NewBasic = editor.getDouble("NewBasic", RnN.NewBasic)
        RnN.NewDA = editor.getDouble("NewDA", RnN.NewDA)
        RnN.NewHRA = editor.getDouble("NewHRA", RnN.NewHRA)
    }

    override fun setSharedPrefsx() {
        sharedPreferences = getSharedPreferences("OcPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putDouble("OldBasic", RnN.OldBasic)
        editor.putDouble("OldDA", RnN.OldDA)
        editor.putDouble("OldHRA", RnN.OldHRA)
        editor.putInt("Days", RnN.Days)
        editor.putDouble("NewBasic", RnN.NewBasic)
        editor.putDouble("NewDA", RnN.NewDA)
        editor.putDouble("NewHRA", RnN.NewHRA)

        editor.apply()
    }


    fun SharedPreferences.Editor.putDouble(key: String, double: Double) {
        putLong(key, java.lang.Double.doubleToRawLongBits(double))
    }

    fun SharedPreferences.getDouble(key: String, default: Double): Double {
        return java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))
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

    fun deleteObsoletePackages() {
        runBlocking {
            launch(Dispatchers.IO) {
                val downloadDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

                if (downloadDirectory.exists() && downloadDirectory.isDirectory) {
                    val files = downloadDirectory.listFiles()

                    if (files != null) {
                        for (file in files) {
                            if (file.isFile) {
                                val fileName = file.name
                                if (fileName.matches(Regex("delete_me( \\(\\d+\\))?\\.apk"))) {
                                    if (file.delete()) {
                                        println("Deleted: $fileName")
                                    } else {
                                        println("Failed to delete: $fileName")
                                    }
                                }
                            }
                        }
                    } else {
                        println("Failed to list files in the download directory.")
                    }
                } else {
                    println("Download directory does not exist or is not a directory.")
                }
            }
        }
    }


}