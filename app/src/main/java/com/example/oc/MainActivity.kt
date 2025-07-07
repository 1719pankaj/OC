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
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.Int


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
        fetchRnNData()

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

    fun fetchRnNData() {
          val db = FirebaseFirestore.getInstance()
        db.collection("RnN").document("RnNData")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    setSharedPrefs("DailyNorms", document.getDouble("DailyNorms")?.toInt()?.toDouble() ?: getSharedPrefs("DailyNorms"))
                    setSharedPrefs("TruckToShed", document.getDouble("TruckToShed")?.toInt()?.toDouble() ?: getSharedPrefs("TruckToShed"))
                    setSharedPrefs("WagonToShed", document.getDouble("WagonToShed")?.toInt()?.toDouble() ?: getSharedPrefs("WagonToShed"))
                    setSharedPrefs("WagonToPlatform", document.getDouble("WagonToPlatform")?.toInt()?.toDouble() ?: getSharedPrefs("WagonToPlatform"))
                    setSharedPrefs("PlatformToShed", document.getDouble("PlatformToShed")?.toInt()?.toDouble() ?: getSharedPrefs("PlatformToShed"))
                    setSharedPrefs("ShedToTruck", document.getDouble("ShedToTruck")?.toInt()?.toDouble() ?: getSharedPrefs("ShedToTruck"))
                    setSharedPrefs("TruckToPlatform", document.getDouble("TruckToPlatform")?.toInt()?.toDouble() ?: getSharedPrefs("TruckToPlatform"))
                    setSharedPrefs("ShedToWagon", document.getDouble("ShedToWagon")?.toInt()?.toDouble() ?: getSharedPrefs("ShedToWagon"))
                    setSharedPrefs("WagonToTruck", document.getDouble("WagonToTruck")?.toInt()?.toDouble() ?: getSharedPrefs("WagonToTruck"))
                    setSharedPrefs("Refilling", document.getDouble("Refilling")?.toInt()?.toDouble() ?: getSharedPrefs("Refilling"))
                    setSharedPrefs("Restacking", document.getDouble("Restacking")?.toInt()?.toDouble() ?: getSharedPrefs("Restacking"))
                    setSharedPrefs("Weightment", document.getDouble("Weightment")?.toInt()?.toDouble() ?: getSharedPrefs("Weightment"))
                    setSharedPrefs("TruckToTruck", document.getDouble("TruckToTruck")?.toInt()?.toDouble() ?: getSharedPrefs("TruckToTruck"))

                    setSharedPrefs("OldDA", document.getDouble("OldDA") ?: getSharedPrefs("OldDA"))
                    setSharedPrefs("OldHRA", document.getDouble("OldHRA") ?: getSharedPrefs("OldHRA"))
                    setSharedPrefs("Days", document.getLong("Days")?.toInt()?.toDouble() ?: getSharedPrefs("Days"))
                    setSharedPrefs("NewDA", document.getDouble("NewDA") ?: getSharedPrefs("NewDA"))
                    setSharedPrefs("NewHRA", document.getDouble("NewHRA") ?: getSharedPrefs("NewHRA"))
                    Log.d("RnN", "Data fetched successfully: $RnN")
                    getSharedPrefs()
                } else {
                    Log.d("RnN", "No such document")
                    getSharedPrefs()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("RnN", "Error getting documents: ", exception)
                getSharedPrefs()
            }
    }


    fun getSharedPrefs() {
        sharedPreferences = getSharedPreferences("OcPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences

        RnN.DailyNorms = editor.getInt("DailyNorms", RnN.DailyNorms)
        RnN.TruckToShed = editor.getInt("TruckToShed", RnN.TruckToShed)
        RnN.WagonToShed = editor.getInt("WagonToShed", RnN.WagonToShed)
        RnN.WagonToPlatform = editor.getInt("WagonToPlatform", RnN.WagonToPlatform)
        RnN.PlatformToShed = editor.getInt("PlatformToShed", RnN.PlatformToShed)
        RnN.ShedToTruck = editor.getInt("ShedToTruck", RnN.ShedToTruck)
        RnN.TruckToPlatform = editor.getInt("TruckToPlatform", RnN.TruckToPlatform)
        RnN.ShedToWagon = editor.getInt("ShedToWagon", RnN.ShedToWagon)
        RnN.WagonToTruck = editor.getInt("WagonToTruck", RnN.WagonToTruck)
        RnN.Refilling = editor.getInt("Refilling", RnN.Refilling)
        RnN.Restacking = editor.getInt("Restacking", RnN.Restacking)
        RnN.Weightment = editor.getInt("Weightment", RnN.Weightment)
        RnN.TruckToTruck = editor.getInt("TruckToTruck", RnN.TruckToTruck)

        RnN.OldBasic = editor.getDouble("OldBasic", RnN.OldBasic)
        RnN.OldDA = editor.getDouble("OldDA", RnN.OldDA)
        RnN.OldHRA = editor.getDouble("OldHRA", RnN.OldHRA)
        RnN.Days = editor.getInt("Days", RnN.Days)
        RnN.NewBasic = editor.getDouble("NewBasic", RnN.NewBasic)
        RnN.NewDA = editor.getDouble("NewDA", RnN.NewDA)
        RnN.NewHRA = editor.getDouble("NewHRA", RnN.NewHRA)
    }

    fun getSharedPrefs(attribute: String): Double {
        sharedPreferences = getSharedPreferences("OcPrefs", Context.MODE_PRIVATE)
        return when (attribute) {
            "DailyNorms" -> sharedPreferences.getInt("DailyNorms", RnN.DailyNorms).toDouble()
            "TruckToShed" -> sharedPreferences.getInt("TruckToShed", RnN.TruckToShed).toDouble()
            "WagonToShed" -> sharedPreferences.getInt("WagonToShed", RnN.WagonToShed).toDouble()
            "WagonToPlatform" -> sharedPreferences.getInt("WagonToPlatform", RnN.WagonToPlatform).toDouble()
            "PlatformToShed" -> sharedPreferences.getInt("PlatformToShed", RnN.PlatformToShed).toDouble()
            "ShedToTruck" -> sharedPreferences.getInt("ShedToTruck", RnN.ShedToTruck).toDouble()
            "TruckToPlatform" -> sharedPreferences.getInt("TruckToPlatform", RnN.TruckToPlatform).toDouble()
            "ShedToWagon" -> sharedPreferences.getInt("ShedToWagon", RnN.ShedToWagon).toDouble()
            "WagonToTruck" -> sharedPreferences.getInt("WagonToTruck", RnN.WagonToTruck).toDouble()
            "Refilling" -> sharedPreferences.getInt("Refilling", RnN.Refilling).toDouble()
            "Restacking" -> sharedPreferences.getInt("Restacking", RnN.Restacking).toDouble()
            "Weightment" -> sharedPreferences.getInt("Weightment", RnN.Weightment).toDouble()
            "TruckToTruck" -> sharedPreferences.getInt("TruckToTruck", RnN.TruckToTruck).toDouble()

            "OldBasic" -> sharedPreferences.getDouble("OldBasic", RnN.OldBasic)
            "OldDA" -> sharedPreferences.getDouble("OldDA", RnN.OldDA)
            "OldHRA" -> sharedPreferences.getDouble("OldHRA", RnN.OldHRA)

            "Days" -> sharedPreferences.getInt("Days", RnN.Days).toDouble()

            "NewBasic" -> sharedPreferences.getDouble("NewBasic", RnN.NewBasic)
            "NewDA" -> sharedPreferences.getDouble("NewDA", RnN.NewDA)
            "NewHRA" -> sharedPreferences.getDouble("NewHRA", RnN.NewHRA)
            else -> 0.0
        }
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

    fun setSharedPrefs(attribute: String, value: Double) {
        sharedPreferences = getSharedPreferences("OcPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        when (attribute) {
            "DailyNorms" -> editor.putInt("DailyNorms", value.toInt())
            "TruckToShed" -> editor.putInt("TruckToShed", value.toInt())
            "WagonToShed" -> editor.putInt("WagonToShed", value.toInt())
            "WagonToPlatform" -> editor.putInt("WagonToPlatform", value.toInt())
            "PlatformToShed" -> editor.putInt("PlatformToShed", value.toInt())
            "ShedToTruck" -> editor.putInt("ShedToTruck", value.toInt())
            "TruckToPlatform" -> editor.putInt("TruckToPlatform", value.toInt())
            "ShedToWagon" -> editor.putInt("ShedToWagon", value.toInt())
            "WagonToTruck" -> editor.putInt("WagonToTruck", value.toInt())
            "Refilling" -> editor.putInt("Refilling", value.toInt())
            "Restacking" -> editor.putInt("Restacking", value.toInt())
            "Weightment" -> editor.putInt("Weightment", value.toInt())
            "TruckToTruck" -> editor.putInt("TruckToTruck", value.toInt())

            "OldBasic" -> editor.putDouble("OldBasic", value)
            "OldDA" -> editor.putDouble("OldDA", value)
            "OldHRA" -> editor.putDouble("OldHRA", value)
            "Days" -> editor.putInt("Days", value.toInt())
            "NewBasic" -> editor.putDouble("NewBasic", value)
            "NewDA" -> editor.putDouble("NewDA", value)
            "NewHRA" -> editor.putDouble("NewHRA", value)
        }

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