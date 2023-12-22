package com.example.oc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.oc.R
import com.example.oc.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var count = 0


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_calcFragment)
        }

        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_rateFragment)
        }

        binding.button4.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_updateFragment)
        }

        binding.devInfoCard.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_aboutFragment)
        }


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}