package com.example.oc.calculators

import com.example.oc.data.CnN
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding

class NormsAndIncentive {
    companion object{



        fun splitDailyBagsOTBags(binding: FragmentCalcBinding) {
            if(CnN.OtHours > 0.0) {
                CnN.OtHourBags = (CnN.PerHeadBags/(RnN.workingHours +CnN.OtHours))* CnN.OtHours
                if (CnN.OtHourBags > CnN.OtHours*RnN.OTNomrs) {
//                    otWithIncentiveCalc()
                } else {
//                    otWoithoutIncentiveCalc()
                }
                CnN.DailyBags = CnN.PerHeadBags - CnN.OtHourBags

                if (CnN.DailyBags < RnN.DailyNorms) {
//                    dailyWithIncentiveCalc()
                } else {
//                    dailyWithoutIncentiveCalc()
                }
            } else {
                CnN.DailyBags = CnN.PerHeadBags

                if (CnN.DailyBags < RnN.DailyNorms) {
//                    dailyWithIncentiveCalc()
                } else {
//                    dailyWithoutIncentiveCalc()
                }
            }

        }


        private fun dailyWithIncentiveCalc() {
            TODO("Not yet implemented")
        }

        private fun dailyWithoutIncentiveCalc() {
            TODO("Not yet implemented")
        }

        private fun otWithIncentiveCalc() {
            TODO("Not yet implemented")
        }

        private fun otWoithoutIncentiveCalc() {
            TODO("Not yet implemented")
        }

    }
}