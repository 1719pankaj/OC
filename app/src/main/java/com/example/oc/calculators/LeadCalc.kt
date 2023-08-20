package com.example.oc.calculators

import com.example.oc.data.CnN
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding

class LeadCalc {

    companion object {
        fun leadTotal(binding: FragmentCalcBinding) {

            CnN.LTotal = CnN.L66_99 * RnN.L66_99 + CnN.L100_132 * RnN.L100_132 + CnN.L133_165 * RnN.L133_165 + CnN.L165Av * RnN.L165Av

            binding.leadTotalTV.text = CnN.LTotal.roundToTwoDecimalPlaces().toString()

        }


        fun Double.roundToTwoDecimalPlaces(): Double {
            return String.format("%.2f", this).toDouble()
        }


    }
}