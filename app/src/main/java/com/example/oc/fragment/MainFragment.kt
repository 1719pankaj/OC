package com.example.oc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.oc.R
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.helloWorldTV.text = count.toString()

        binding.button.setOnClickListener {
            count++
            binding.helloWorldTV.text = count.toString()
        }

        binding.button2.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_calcFragment)
        }

        binding.button3.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_rateFragment)
        }

        binding.textView.text = RnN.Owner

        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}