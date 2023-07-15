package com.example.oc.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentRateBinding


class RateFragment : Fragment() {


    private var _binding: FragmentRateBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRateBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textView.text = RnN.Owner

        binding.truckToShedTV.text = RnN.TruckToShed.toString()
        binding.wagonToShedTV.text = RnN.WagonToShed.toString()
        binding.wagonToPlatformTV.text = RnN.WagonToPlatform.toString()
        binding.platformToShedTV.text = RnN.PlatformToShed.toString()
        binding.shedToTruckTV.text = RnN.ShedToTruck.toString()
        binding.truckToPlatformTV.text = RnN.TruckToPlatform.toString()
        binding.shedToWagonTV.text = RnN.ShedToWagon.toString()
        binding.wagonToTruckTV.text = RnN.WagonToTruck.toString()
        binding.refillingTV.text = RnN.Refilling.toString()
        binding.restackingTV.text = RnN.Restacking.toString()
        binding.weightmentTV.text = RnN.Weightment.toString()
        binding.truckToTruckTV.text = RnN.TruckToTruck.toString()


        return view
    }

}