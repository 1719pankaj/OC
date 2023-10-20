package com.example.oc.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dcastalia.localappupdate.DownloadApk
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray


class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    var latest_version = ""
    var download_url = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.currentVersionLab.text = RnN.CurrentVersion

        val data = arguments?.getString("direct_download_flag")
        if (data != null) {
            DirectUpdateRunner()
        }

        UpdateRunner()

        binding.checkUpdateBT.setOnClickListener {
            UpdateRunner()
        }

        binding.downloadBT.setOnClickListener {
            DownloadAndInstall()
        }

        return view
    }

    private fun DownloadAndInstall() {
        val downloadApk = DownloadApk(requireContext())
        downloadApk.startDownloadingApk(download_url)
    }

    private fun UpdateRunner() {
        CoroutineScope(Dispatchers.IO).launch {
            CheckUpdates()
            withContext(Dispatchers.Main) {
                if (latest_version > RnN.CurrentVersion) {
                    binding.updateAvailableHD.text = "Update Available"
                    binding.downloadBT.text = latest_version
                    binding.updateCardView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun DirectUpdateRunner() {
        CoroutineScope(Dispatchers.IO).launch {
            CheckUpdates()
            withContext(Dispatchers.Main) {
                if (latest_version > RnN.CurrentVersion) {
                    DownloadAndInstall()
                }
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
                download_url = "https://github.com/1719pankaj/OC/releases/download/$latest_version/$latest_version.apk"
            }
        } catch (e: Exception) {
            Log.e("ReleaseCheckWorker", "Error checking for new release", e)
        }
    }


}