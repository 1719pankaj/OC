package com.example.oc.calculators

import com.example.oc.data.CnN
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding

class HeightCalc {
    companion object {
        fun heightTotal(binding: FragmentCalcBinding) {

            CnN.HTotal = CnN.H11_12 * RnN.H11_12 + CnN.H13_14 * RnN.H13_14 + CnN.H15_16 * RnN.H15_16 + CnN.H17_18 * RnN.H17_18 + CnN.H19_20 * RnN.H19_20

            binding.heightTotalTV.text = CnN.HTotal.roundToTwoDecimalPlaces().toString()

        }


        fun Double.roundToTwoDecimalPlaces(): Double {
            return String.format("%.2f", this).toDouble()
        }


    }
}