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

        binding.oldBasicET.setText(String.format("%.0f", RnN.OldBasic))
        binding.oldDaET.setText(RnN.OldDA.toString())
        binding.oldHraET.setText(RnN.OldHRA.toString())
        binding.daysET.setText(RnN.Days.toString())

        val oldBasic = RnN.OldBasic
        val oldDA = RnN.OldDA
        val oldHRA = RnN.OldHRA
        val days = RnN.Days
        val norms = RnN.TruckToShed

        fun Double.roundToTwoDecimalPlaces(): Double {
            return String.format("%.2f", this).toDouble()
        }

        val perDayBasic = (oldBasic / 26).roundToTwoDecimalPlaces()
        val perDayDA = ((oldBasic * (oldDA / 100) * 12) / days).roundToTwoDecimalPlaces()
        val perDayHRA = ((oldBasic * (oldHRA / 100) * 12) / days).roundToTwoDecimalPlaces()
        val perDayTotal = (perDayBasic + perDayDA + perDayHRA).roundToTwoDecimalPlaces()
        val perDayBags = (perDayTotal / norms).roundToTwoDecimalPlaces()


        binding.upperBasicTV.text = perDayBasic.toString()
        binding.upperDaTV.text = perDayDA.toString()
        binding.upperHraTV.text = perDayHRA.toString()
        binding.upperPerDayTV.text = perDayTotal.toString()
        binding.upperPerBagTV.text = perDayBags.toString()


        binding.newBasicET.setText(String.format("%.0f", RnN.NewBasic))
        binding.newDaET.setText(RnN.NewDA.toString())
        binding.newHraET.setText(RnN.NewHRA.toString())


        return view
    }

}