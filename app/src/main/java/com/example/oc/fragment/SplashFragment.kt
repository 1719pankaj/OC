package com.example.oc.fragment

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.oc.R
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        Handler().postDelayed({
            if (!checkReturingUser()) {
                Toast.makeText(requireContext(), "New User Detected", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_splashFragment2_to_itsMyFirstTime)
            } else {
                //check if notifications are granted
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (!notificationsAreEnabled())
                        findNavController().navigate(R.id.action_splashFragment2_to_notificationFragment)
                    else
                        findNavController().navigate(R.id.action_splashFragment2_to_mainFragment)
                } else
                    findNavController().navigate(R.id.action_splashFragment2_to_mainFragment)
            }
        }, 2000)

        //if first time, navigate to itsMyFirstTime
        //else navigate to mainFragment


        return view
    }

    private fun notificationsAreEnabled(): Boolean {
        val notificationManagerCompat = NotificationManagerCompat.from(requireContext())
        val areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled()
        return areNotificationsEnabled
    }

    //get shared preference for boolean
    //if true, navigate to itsMyFirstTime
    fun checkReturingUser(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("ReturningUser", MODE_PRIVATE)
        return sharedPref.getBoolean("ReturningUser", false)
    }


}