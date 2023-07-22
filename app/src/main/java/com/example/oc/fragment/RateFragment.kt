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

        binding.oldBasicET.isEnabled = false
        binding.oldDaET.isEnabled = false
        binding.oldHraET.isEnabled = false
        binding.daysET.isEnabled = false
        binding.newBasicET.isEnabled = false
        binding.newDaET.isEnabled = false
        binding.newHraET.isEnabled = false

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


        binding.newBasicET.setText(String.format("%.0f", RnN.NewBasic))
        binding.newDaET.setText(RnN.NewDA.toString())
        binding.newHraET.setText(RnN.NewHRA.toString())

        calculateDerivedValues()

        binding.height1112TV.text = RnN.H11_12.toString()
        binding.height1314TV.text = RnN.H13_14.toString()
        binding.height1516TV.text = RnN.H15_16.toString()
        binding.height1718TV.text = RnN.H17_18.toString()
        binding.height1920TV.text = RnN.H19_20.toString()

        binding.lead6699TV.text = RnN.L66_99.toString()
        binding.lead100132TV.text = RnN.L100_132.toString()
        binding.lead133165TV.text = RnN.L133_165.toString()
        binding.lead165AndAboveTV.text = RnN.L165Av.toString()

        binding.updateBT.setOnClickListener {
            editRates()
        }

        return view
    }
    //Set all EditTexts to editable = true
    //Change Update button text to 'Save'
    //Once user hits save, update the values in RnN
    //Set all EditTexts to editable = false
    //Change Update button text to 'Update'
    //Calculate derived values
    private fun editRates() {
        if (binding.updateBT.text == "Update") {
            binding.oldBasicET.isEnabled = true
            binding.oldDaET.isEnabled = true
            binding.oldHraET.isEnabled = true
            binding.daysET.isEnabled = true
            binding.newBasicET.isEnabled = true
            binding.newDaET.isEnabled = true
            binding.newHraET.isEnabled = true
            binding.updateBT.text = "Save"
        } else if (binding.updateBT.text == "Save") {
            binding.oldBasicET.isEnabled = false
            binding.oldDaET.isEnabled = false
            binding.oldHraET.isEnabled = false
            binding.daysET.isEnabled = false
            binding.newBasicET.isEnabled = false
            binding.newDaET.isEnabled = false
            binding.newHraET.isEnabled = false
            binding.updateBT.text = "Update"
            updateRates()
            calculateDerivedValues()
        }
    }

    private fun updateRates() {
        RnN.OldBasic = binding.oldBasicET.text.toString().toDouble()
        RnN.OldDA = binding.oldDaET.text.toString().toDouble()
        RnN.OldHRA = binding.oldHraET.text.toString().toDouble()
        RnN.Days = binding.daysET.text.toString().toInt()
        RnN.NewBasic = binding.newBasicET.text.toString().toDouble()
        RnN.NewDA = binding.newDaET.text.toString().toDouble()
        RnN.NewHRA = binding.newHraET.text.toString().toDouble()
    }

    fun calculateDerivedValues() {
        val oldBasic = RnN.OldBasic
        val oldDA = RnN.OldDA
        val oldHRA = RnN.OldHRA
        val days = RnN.Days
        val norms = RnN.TruckToShed

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


        val newBasic = RnN.NewBasic
        val newDA = RnN.NewDA
        val newHRA = RnN.NewHRA

        val perDayBasicNew = (newBasic / 26).roundToTwoDecimalPlaces()
        val perDayDANew = ((newBasic * (newDA / 100) * 12) / days).roundToTwoDecimalPlaces()
        val perDayHRANew = ((newBasic * (newHRA / 100) * 12) / days).roundToTwoDecimalPlaces()
        val perDayTotalNew = (perDayBasicNew + perDayDANew + perDayHRANew).roundToTwoDecimalPlaces()
        val perHourTotal = ((perDayTotalNew / 7)*1.1).roundToTwoDecimalPlaces()

        binding.lowerBasicTV.text = perDayBasicNew.toString()
        binding.lowerDaTV.text = perDayDANew.toString()
        binding.lowerHraTV.text = perDayHRANew.toString()
        binding.lowerPerDayTV.text = perDayTotalNew.toString()
        binding.lowerPerHourTV.text = perHourTotal.toString()

    }

    fun Double.roundToTwoDecimalPlaces(): Double {
        return String.format("%.2f", this).toDouble()
    }

}