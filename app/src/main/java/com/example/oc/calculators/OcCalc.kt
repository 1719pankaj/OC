package com.example.oc.calculators

import com.example.oc.data.CnN
import com.example.oc.data.RnN

class OcCalc {

    companion object {

        fun otIncentiveCalc() {
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