package com.example.oc.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.oc.MainActivity
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentRateBinding


class RateFragment : Fragment() {


    private var _binding: FragmentRateBinding? = null

    private val binding get() = _binding!!

    private var callback: MyCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Check if the context (activity) implements the interface
        if (context is MyCallback) {
            callback = context
        } else {
            throw RuntimeException("$context must implement MyCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRateBinding.inflate(inflater, container, false)
        val view = binding.root


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

        binding.dailyNormsTV.text = RnN.DailyNorms.toString()
        binding.otNormsTV.text = RnN.OTNomrs.toString()

        binding.oldBasicET.setText(String.format("%.0f", RnN.OldBasic))
        binding.oldDaET.setText(RnN.OldDA.toString())
        binding.oldHraET.setText(RnN.OldHRA.toString())
        binding.daysET.setText(RnN.Days.toString())


        binding.newBasicET.setText(String.format("%.0f", RnN.NewBasic))
        binding.newDaET.setText(RnN.NewDA.toString())
        binding.newHraET.setText(RnN.NewHRA.toString())

        updateDerivedValueViews()

        binding.updateBT.setOnClickListener {
            editRates()
        }

        return view
    }

    private fun updateDerivedValueViews() {
        RnN.calculateDerivedValues()

        binding.upperBasicTV.text = RnN.perDayBasic.roundToTwoDecimalPlaces().toString()
        binding.upperDaTV.text = RnN.perDayDA.roundToTwoDecimalPlaces().toString()
        binding.upperHraTV.text = RnN.perDayHRA.roundToTwoDecimalPlaces().toString()
        binding.upperPerDayTV.text = RnN.perDayTotal.roundToTwoDecimalPlaces().toString()
        binding.upperPerBagTV.text = RnN.perDayBags.roundToTwoDecimalPlaces().toString()


        binding.lowerBasicTV.text = RnN.perDayBasicNew.roundToTwoDecimalPlaces().toString()
        binding.lowerDaTV.text = RnN.perDayDANew.roundToTwoDecimalPlaces().toString()
        binding.lowerHraTV.text = RnN.perDayHRANew.roundToTwoDecimalPlaces().toString()
        binding.lowerPerDayTV.text = RnN.perDayTotalNew.roundToTwoDecimalPlaces().toString()
        binding.lowerPerHourTV.text = RnN.perHourTotal.roundToTwoDecimalPlaces().toString()



        binding.height1112TV.text = RnN.H11_12.roundToTwoDecimalPlaces().toString()
        binding.height1314TV.text = RnN.H13_14.roundToTwoDecimalPlaces().toString()
        binding.height1516TV.text = RnN.H15_16.roundToTwoDecimalPlaces().toString()
        binding.height1718TV.text = RnN.H17_18.roundToTwoDecimalPlaces().toString()
        binding.height1920TV.text = RnN.H19_20.roundToTwoDecimalPlaces().toString()

        binding.lead6699TV.text = RnN.L66_99.roundToTwoDecimalPlaces().toString()
        binding.lead100132TV.text = RnN.L100_132.roundToTwoDecimalPlaces().toString()
        binding.lead133165TV.text = RnN.L133_165.roundToTwoDecimalPlaces().toString()
        binding.lead165AndAboveTV.text = RnN.L165Av.roundToTwoDecimalPlaces().toString()

        binding.firstSlabTV.text = RnN.firstSlab.roundToTwoDecimalPlaces().toString()
        binding.secondSlabTV.text = RnN.secondSlab.roundToTwoDecimalPlaces().toString()
        binding.thirdSlabTV.text = RnN.thirdSlab.roundToTwoDecimalPlaces().toString()
        binding.fourthSlabTV.text = RnN.fourthSlab.roundToTwoDecimalPlaces().toString()
    }

    private fun editRates() {
        if (binding.updateBT.text == "Edit") {
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
            binding.updateBT.text = "Edit"
            updateRates()
            RnN.calculateDerivedValues()
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
        updateDerivedValueViews()
        setSharedPrefs()

    }

    fun setSharedPrefs() {
        callback?.setSharedPrefsx()
    }


    fun Double.roundToTwoDecimalPlaces(): Double {
        return String.format("%.2f", this).toDouble()
    }

}

interface MyCallback {
    fun setSharedPrefsx()
}

