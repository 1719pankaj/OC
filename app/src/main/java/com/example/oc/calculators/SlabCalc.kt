package com.example.oc.calculators

import com.example.oc.data.CnN
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding

class SlabCalc {
    companion object{



        fun splitDailyBagsOTBags(binding: FragmentCalcBinding) {

            if(CnN.weekDay) {
                CnN.workingHours = 0.0
            } else {
                CnN.workingHours = RnN.workingHours
            }

            CnN.OtHourBags = (CnN.PerHeadBags/(CnN.workingHours +CnN.OtHours))* CnN.OtHours
            CnN.DailyBags = CnN.PerHeadBags - CnN.OtHourBags

            if(CnN.DailyBags > 0) dailyIncentiveCalc()
            if(CnN.OtHourBags > 0) otIncentiveCalc()

        }

        private fun dailyIncentiveCalc() {
            if (CnN.DailyBags > RnN.DailyNorms) {
                CnN.DailyAboveNorms = CnN.DailyBags - RnN.DailyNorms
                CnN.DailyWithinNorms = RnN.DailyNorms.toDouble()

                val dailyBags = CnN.DailyAboveNorms
                val valuePerVariable = 30.0

                CnN.FirstSlabBags = minOf(dailyBags, valuePerVariable)
                CnN.SecondSlabBags = minOf(dailyBags - CnN.FirstSlabBags, valuePerVariable)
                CnN.ThirdSlabBags = minOf(dailyBags - CnN.FirstSlabBags - CnN.SecondSlabBags, valuePerVariable)
                CnN.FourthSlabBags = maxOf(dailyBags - CnN.FirstSlabBags - CnN.SecondSlabBags - CnN.ThirdSlabBags, 0.0)

                CnN.DailyIncentive = CnN.FirstSlabBags * RnN.firstSlab + CnN.SecondSlabBags * RnN.secondSlab + CnN.ThirdSlabBags * RnN.thirdSlab + CnN.FourthSlabBags * RnN.fourthSlab
            }
        }

        private fun otIncentiveCalc() {
            CnN.OtWithinNorms = CnN.OtHours * RnN.perHourTotal
            if(CnN.OtHourBags > RnN.OTNomrs * CnN.OtHours) {
                CnN.OtAboveNorms = CnN.OtHourBags - RnN.OTNomrs * CnN.OtHours


                val otBags = CnN.OtAboveNorms
                val valuePerVariable = 5 * CnN.OtHours

                CnN.OtFirstSlabBags = minOf(otBags, valuePerVariable)
                CnN.OtSecondSlabBags = minOf(otBags - CnN.OtFirstSlabBags, valuePerVariable)
                CnN.OtThirdSlabBags = minOf(otBags - CnN.OtFirstSlabBags - CnN.OtSecondSlabBags, valuePerVariable)
                CnN.OtFourthSlabBags = maxOf(otBags - CnN.OtFirstSlabBags - CnN.OtSecondSlabBags - CnN.OtThirdSlabBags, 0.0)

                CnN.OtIncentive = CnN.OtFirstSlabBags * RnN.firstSlab + CnN.OtSecondSlabBags * RnN.secondSlab + CnN.OtThirdSlabBags * RnN.thirdSlab + CnN.OtFourthSlabBags * RnN.fourthSlab
            }
        }


    }
}