package com.example.oc.fragment

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.dcastalia.localappupdate.DownloadApk
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    var latest_version = ""
    var download_url = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.currentVersionLab.text = RnN.CurrentVersion

        binding.checkUpdateBT.setOnClickListener {
            UpdateRunner()
        }

        binding.downloadBT.setOnClickListener {
            downloadFile(requireContext(), download_url)

//            val downloadApk = DownloadApk(requireContext())
//            downloadApk.startDownloadingApk(download_url)
        }

        binding.installBT.setOnClickListener {
            val appDataDir = requireContext().getExternalFilesDir(null)
            val downloadsDir = File(appDataDir, "downloads")
            val fileName = "app-debug.apk"
            val destinationFile = File(downloadsDir, fileName)
            installApk(requireContext(), destinationFile)
        }
        return view
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

    private fun UpdateRunner() {
        CoroutineScope(Dispatchers.IO).launch {
            CheckUpdates()
            withContext(Dispatchers.Main) {
                UpdateView()
            }
        }
    }

    private fun UpdateView() {

        if (latest_version > RnN.CurrentVersion) {
            binding.updateAvailableHD.text = "Update Available"
            binding.downloadBT.text = latest_version
            binding.updateCardView.visibility = View.VISIBLE
            download_url = "https://github.com/1719pankaj/OC/releases/download/$latest_version/$latest_version.apk"
            if(!cleanDownloadsDirectory( File(requireContext().getExternalFilesDir(null), "downloads"), latest_version)){
                binding.installBT.visibility = View.VISIBLE
            }
        }

    }

    private fun CheckUpdates() {
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
                latest_version = latestRelease.getString("tag_name")
            }
        } catch (e: Exception) {
            Log.e("ReleaseCheckWorker", "Error checking for new release", e)
        }
    }

    fun cleanDownloadsDirectory(downloadsDir: File, latestVersion: String): Boolean {
        // Check if latest_version.apk already exists in the downloads directory
        val latestVersionFile = File(downloadsDir, "$latestVersion.apk")
        if (latestVersionFile.exists()) {
            val files = downloadsDir.listFiles()
            files?.forEach { file ->
                if (file != latestVersionFile) {
                    file.delete()
                }
            }
            return false
        }
        return true
    }

    fun downloadFile(context: Context, fileUrl: String) {
        val appDataDir = context.getExternalFilesDir(null)
        val downloadsDir = File(appDataDir, "downloads")

        // Create the downloads directory if it doesn't exist
        downloadsDir.mkdirs()

        val fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1)
        val destinationFile = File(downloadsDir, fileName)
        binding.progressBar.visibility = View.VISIBLE
        binding.installBT.visibility = View.GONE

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(fileUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connect()

                // Input stream to read the file
                val input: InputStream = connection.inputStream

                // Output stream to save the file
                val output = FileOutputStream(destinationFile)

                val data = ByteArray(4096)
                var total: Long = 0
                var count: Int

                while (input.read(data).also { count = it } != -1) {
                    total += count.toLong()
                    output.write(data, 0, count)
                }

                output.flush()
                output.close()
                input.close()

                // Rename the file to "app-debug.apk"
                val newFileName = "app-debug.apk"
                val renamedFile = File(downloadsDir, newFileName)
                destinationFile.renameTo(renamedFile)

                // Show a toast notification when the download is complete
                GlobalScope.launch(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    binding.installBT.visibility = View.VISIBLE

                    Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                // Show a toast for download failure
                GlobalScope.launch(Dispatchers.Main) {
                    Toast.makeText(context, "Download failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    fun installApk(context: Context, apkFile: File): Boolean {
        try {
            // Create an Intent to install the APK
            val intent = Intent(Intent.ACTION_VIEW)
            val apkUri = FileProvider.getUriForFile(
                context,
                "com.example.oc.provider",
                apkFile
            )
            intent.data = apkUri
            intent.type = "application/vnd.android.package-archive"
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            // Start the installation
            context.startActivity(intent)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }



}