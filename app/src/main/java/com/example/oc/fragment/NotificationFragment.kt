package com.example.oc.fragment

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.oc.MainActivity
import com.example.oc.R
import com.example.oc.databinding.FragmentNotificationBinding
import com.example.oc.databinding.FragmentSplashBinding


class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null

    private lateinit var requestLauncher: ActivityResultLauncher<String>

    private val binding get() = _binding!!
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val view = binding.root

        requestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                findNavController().navigate(R.id.action_notificationFragment_to_mainFragment)
            }
            else {
                binding.getNotifiedDescTV.text = "Please enable notifications from settings and/or reopen app"
            }
        }

        binding.agreeNotificationBT.setOnClickListener {
                askForNotificationPermission()
        }

        return view
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun askForNotificationPermission() {
        requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }



}