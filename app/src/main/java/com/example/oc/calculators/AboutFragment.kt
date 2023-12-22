package com.example.oc.calculators

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.oc.databinding.FragmentAboutBinding
import com.example.oc.databinding.FragmentMainBinding


class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.imageView.setOnClickListener{
            openGithub()
        }





        return view
    }
    //open a browser to the github page.
    fun openGithub() {
        val url = "https://github.com/1719pankaj/OC"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}