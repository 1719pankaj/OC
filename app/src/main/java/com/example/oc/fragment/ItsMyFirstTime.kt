package com.example.oc.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.oc.R
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentItsMyFirstTimeBinding
import com.example.oc.databinding.FragmentMainBinding
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class ItsMyFirstTime : Fragment() {

    private var _binding: FragmentItsMyFirstTimeBinding? = null
    private val binding get() = _binding!!

    private lateinit var key: String
    private lateinit var code: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentItsMyFirstTimeBinding.inflate(inflater, container, false)
        val view = binding.root


        key = hashAndroidId(requireContext()).toString().uppercase()
        code = getUnlockKey(key)
        binding.keyTV.text = key

        binding.verifyBT.setOnClickListener {
            if (binding.codeET.text.toString() == code) {
                setFirstTime()
                findNavController().navigate(R.id.action_itsMyFirstTime_to_notificationFragment)
            } else {
                Toast.makeText(context, "Enter correct C0D3!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.copySendBT.setOnClickListener {
            copy_and_send()
        }



        return view
    }

    //set shared preference to false
    private fun setFirstTime() {
        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences("FirstTime", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("FirstTime", false)
        editor.apply()
    }



    fun copy_and_send() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+91${RnN.MaalkiKaNumber}&text=${key}"))
        startActivity(intent)
    }

    fun hashAndroidId(context: Context): String? {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        // Hashing the Android ID using SHA-256
        val md = MessageDigest.getInstance("SHA-256")
        val androidIdHash = md.digest(androidId.toByteArray(StandardCharsets.UTF_8))

        // Convert the hash to a 6-character alphanumeric string
        val base64String = Base64.encodeToString(androidIdHash, Base64.NO_PADDING or Base64.NO_WRAP)
        val alphanumericString = base64String
            .replace(Regex("[^a-zA-Z0-9]"), "") // Remove non-alphanumeric characters
            .take(6) // Take the first 6 characters

        return alphanumericString
    }

    fun getUnlockKey(key: String): String {
        val hmacSha256Key = SecretKeySpec(key.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(hmacSha256Key)
        val input = "hii_my_name_is_pankaj"
        val hmacResult = mac.doFinal(input.toByteArray(StandardCharsets.UTF_8))
        val base64String = Base64.encodeToString(hmacResult, Base64.NO_WRAP or Base64.NO_PADDING)
        val code64Bit = base64String.substring(0, 8)

        return code64Bit
    }


}