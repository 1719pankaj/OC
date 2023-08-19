package com.example.oc.calculators

import com.example.oc.data.CnN
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding
import java.text.DecimalFormat

class EquivalentBags {

    companion object {
        fun truckToShedCalc(binding: FragmentCalcBinding) {
            val truckToShed = binding.truckToShedTV.text.toString().toDoubleOrNull() ?: 0.0
            val truckToShedOOW = binding.truckToShedOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(truckToShed != 0.0) {
                CnN.TruckToShedTotal = ((truckToShed - truckToShedOOW) + ((truckToShedOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.TruckToShedTotal)
                binding.truckToShedTotalTV.text = total.toString()
            } else {
                CnN.TruckToShedTotal = 0.0
                binding.truckToShedTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun wagonToShedCalc(binding: FragmentCalcBinding) {
            val wagonToShed = binding.wagonToShedTV.text.toString().toDoubleOrNull() ?: 0.0
            val wagonToShedOOW = binding.wagonToShedOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(wagonToShed != 0.0) {
                CnN.WagonToShedTotal = ((((wagonToShed - wagonToShedOOW)/RnN.WagonToShed)*RnN.DailyNorms) + ((wagonToShedOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.WagonToShedTotal)
                binding.wagonToShedTotalTV.text = total.toString()
            } else {
                CnN.WagonToShedTotal = 0.0
                binding.wagonToShedTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun wagonToPlatformCalc(binding: FragmentCalcBinding) {
            val wagonToPlatform = binding.wagonToPlatformTV.text.toString().toDoubleOrNull() ?: 0.0
            val wagonToPlatformOOW = binding.wagonToPlatformOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(wagonToPlatform != 0.0) {
                CnN.WagonToPlatformTotal = ((((wagonToPlatform - wagonToPlatformOOW)/RnN.WagonToPlatform)*RnN.DailyNorms) + ((wagonToPlatformOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.WagonToPlatformTotal)
                binding.wagonToPlatformTotalTV.text = total.toString()
            } else {
                CnN.WagonToPlatformTotal = 0.0
                binding.wagonToPlatformTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun platformToShedCalc(binding: FragmentCalcBinding) {
            val platformToShed = binding.platformToShedTV.text.toString().toDoubleOrNull() ?: 0.0
            val platformToShedOOW = binding.platformToShedOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(platformToShed != 0.0) {
                CnN.PlatformToShedTotal = ((((platformToShed - platformToShedOOW)/RnN.PlatformToShed)*RnN.DailyNorms) + ((platformToShedOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.PlatformToShedTotal)
                binding.platformToShedTotalTV.text = total.toString()
            } else {
                CnN.PlatformToShedTotal = 0.0
                binding.platformToShedTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun shedToTruckCalc(binding: FragmentCalcBinding) {
            val shedToTruck = binding.shedToTruckTV.text.toString().toDoubleOrNull() ?: 0.0
            val shedToTruckOOW = binding.shedToTruckOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(shedToTruck != 0.0) {
                CnN.ShedToTruckTotal = ((((shedToTruck - shedToTruckOOW)/RnN.ShedToTruck)*RnN.DailyNorms) + ((shedToTruckOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.ShedToTruckTotal)
                binding.shedToTruckTotalTV.text = total.toString()
            } else {
                CnN.ShedToTruckTotal = 0.0
                binding.shedToTruckTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun truckToPlatformCalc(binding: FragmentCalcBinding) {
            val truckToPlatform = binding.truckToPlatformTV.text.toString().toDoubleOrNull() ?: 0.0
            val truckToPlatformOOW = binding.truckToPlatformOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(truckToPlatform != 0.0) {
                CnN.TruckToPlatformTotal = ((((truckToPlatform - truckToPlatformOOW)/RnN.TruckToPlatform)*RnN.DailyNorms) + ((truckToPlatformOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.TruckToPlatformTotal)
                binding.truckToPlatformTotalTV.text = total.toString()
            } else {
                CnN.TruckToPlatformTotal = 0.0
                binding.truckToPlatformTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun shedToWagonCalc(binding: FragmentCalcBinding) {
            val shedToWagon = binding.shedToWagonTV.text.toString().toDoubleOrNull() ?: 0.0
            val shedToWagonOOW = binding.shedToWagonOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(shedToWagon != 0.0) {
                CnN.ShedToWagonTotal = ((((shedToWagon - shedToWagonOOW)/RnN.ShedToWagon)*RnN.DailyNorms) + ((shedToWagonOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.ShedToWagonTotal)
                binding.shedToWagonTotalTV.text = total.toString()
            } else {
                CnN.ShedToWagonTotal = 0.0
                binding.shedToWagonTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun wagonToTruckCalc(binding: FragmentCalcBinding) {
            val wagonToTruck = binding.wagonToTruckTV.text.toString().toDoubleOrNull() ?: 0.0
            val wagonToTruckOOW = binding.wagonToTruckOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(wagonToTruck != 0.0) {
                CnN.WagonToTruckTotal = ((((wagonToTruck - wagonToTruckOOW)/RnN.WagonToTruck)*RnN.DailyNorms) + ((wagonToTruckOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.WagonToTruckTotal)
                binding.wagonToTruckTotalTV.text = total.toString()
            } else {
                CnN.WagonToTruckTotal = 0.0
                binding.wagonToTruckTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun refillingCalc(binding: FragmentCalcBinding) {
            val refilling = binding.refillingTV.text.toString().toDoubleOrNull() ?: 0.0
            if(refilling != 0.0) {
                CnN.RefillingTotal = ((refilling/RnN.Refilling)*RnN.DailyNorms)
                val total = DecimalFormat("#.##").format(CnN.RefillingTotal)
                binding.refillingTotalTV.text = total.toString()
            } else {
                CnN.RefillingTotal = 0.0
                binding.refillingTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun restackingCalc(binding: FragmentCalcBinding) {
            val restacking = binding.restackingTV.text.toString().toDoubleOrNull() ?: 0.0
            val restackingOOW = binding.restackingOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(restacking != 0.0) {
                CnN.RestackingTotal = ((((restacking - restackingOOW)/RnN.Restacking)*RnN.DailyNorms) + ((restackingOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.RestackingTotal)
                binding.restackingTotalTV.text = total.toString()
            } else {
                CnN.RestackingTotal = 0.0
                binding.restackingTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun truckToTruckCalc(binding: FragmentCalcBinding) {
            val truckToTruck = binding.truckToTruckTV.text.toString().toDoubleOrNull() ?: 0.0
            val truckToTruckOOW = binding.truckToTruckOowTV.text.toString().toDoubleOrNull() ?: 0.0
            if(truckToTruck != 0.0) {
                CnN.TruckToTruckTotal = ((((truckToTruck - truckToTruckOOW)/RnN.TruckToTruck)*RnN.DailyNorms) + ((truckToTruckOOW/55)*RnN.DailyNorms))
                val total = DecimalFormat("#.##").format(CnN.TruckToTruckTotal)
                binding.truckToTruckTotalTV.text = total.toString()
            } else {
                CnN.TruckToTruckTotal = 0.0
                binding.truckToTruckTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun weightmentCalc(binding: FragmentCalcBinding) {
            val weightment = binding.weightmentTV.text.toString().toDoubleOrNull() ?: 0.0
            if(weightment != 0.0) {
                CnN.WeightmentTotal = ((weightment/RnN.Weightment)*RnN.DailyNorms)
                val total = DecimalFormat("#.##").format(CnN.WeightmentTotal)
                binding.weightmentTotalTV.text = total.toString()
            } else {
                CnN.WeightmentTotal = 0.0
                binding.weightmentTotalTV.text = "0.0"
            }
            updateTotalBags(binding)
        }

        fun updateHeadCount(binding: FragmentCalcBinding) {
            val headCount = binding.headCountET.text.toString().toIntOrNull() ?: 0
            if(headCount != 0)
                CnN.HeadCount = headCount
            else
                CnN.HeadCount = 0
            updateTotalBags(binding)

        }

        fun updateTotalBags(binding: FragmentCalcBinding) {
            CnN.TotalBags = CnN.WeightmentTotal + CnN.TruckToTruckTotal + CnN.RestackingTotal +
                    CnN.RefillingTotal + CnN.WagonToTruckTotal + CnN.ShedToWagonTotal +
                    CnN.TruckToPlatformTotal + CnN.ShedToTruckTotal +
                    CnN.PlatformToShedTotal + CnN.WagonToPlatformTotal +
                    CnN.WagonToShedTotal + CnN.TruckToShedTotal
            binding.totalBagsTV.text = DecimalFormat("#.##").format(CnN.TotalBags)
            CnN.PerHeadBags = CnN.TotalBags/CnN.HeadCount
            binding.textView.text = CnN.PerHeadBags.toString()
            normsCalc(binding)

        }

        ///////*****************************************************/////
        fun normsCalc(binding: FragmentCalcBinding) {
            if (binding.weekdaySwitch.isChecked) {
                // If Holiday //
                CnN.OtHourBag = CnN.PerHeadBags - (CnN.OtHours * RnN.OtNorms)
                binding.otFirst10SlaveET.setText(CnN.OtHourBag.toString())
                CnN.DailyBags = 0.0
                binding.first30SlaveET.setText(CnN.DailyBags.toString())
            }
            else{
                // If WorkDay //
                CnN.OtHourBag = ((CnN.PerHeadBags/(7+CnN.OtHours))* CnN.OtHours) -CnN.OtHours * RnN.OtNorms
                binding.otFirst10SlaveET.setText(CnN.OtHourBag.toString())
                CnN.DailyBags = (CnN.PerHeadBags - CnN.OtHourBag)- RnN.DailyNorms
                binding.first30SlaveET.setText(CnN.DailyBags.toString())
            }

            //------------------------------------------------------------------------//
            // Slabe--OT1 //
            if ((CnN.OtHourBag < CnN.OtHours * 5) and (CnN.OtHours > 0)){
               CnN.OtSlave1 = CnN.OtHourBag
                binding.otFirst10SlaveET.setText(CnN.OtSlave2.toString())
            }
            if ((CnN.OtHourBag >= CnN.OtHours * 5) and (CnN.OtHours > 0)){
                CnN.OtSlave1 = CnN.OtHours * 5
                binding.otFirst10SlaveET.setText(CnN.OtSlave2.toString())
            }
            // Slave--OT2 //
            if ((CnN.OtHourBag < CnN.OtHours * 10)and (CnN.OtHours > 5)){
                CnN.OtSlave2 = CnN.OtHourBag - CnN.OtSlave1

            }
            if ((CnN.OtHourBag >= CnN.OtHours * 10) and (CnN.OtHours > 5)){
                CnN.OtSlave2 = CnN.OtHours * 5

            }
            // Slave--OT3 //
            if ((CnN.OtHourBag < CnN.OtHours * 30) and (CnN.OtHours > 10)){
                CnN.OtSlave3 = CnN.OtHourBag - CnN.OtSlave1
            }
            if ((CnN.OtHourBag >= CnN.OtHours * 30) and (CnN.OtHours > 10)){
                CnN.OtSlave3 = CnN.OtHours * 5
            }
            //------------------------------------------------------------------------//
            // Slave-1 //
            if ( (CnN.DailyBags < 30) and (CnN.DailyBags > 0)){
                CnN.DailySlave1 = CnN.DailyBags


            }
            if ( (CnN.DailyBags >= 30) and (CnN.DailyBags > 0)){
                CnN.DailySlave1 = 30.00
            }
            // Slave-2 //
            if ( (CnN.DailyBags < 60) and (CnN.DailyBags > 0)){
                CnN.DailySlave2 = CnN.DailyBags - 30
                binding.second30SlaveET.setText(CnN.DailySlave2.toString())
            }
            if ( (CnN.DailyBags >= 60) and (CnN.DailyBags > 0)){
                CnN.DailySlave2 = 30.00
                binding.second30SlaveET.setText(CnN.DailySlave2.toString())
            }
            // Slave-3 //
            if ( (CnN.DailyBags < 90) and (CnN.DailyBags > 0)){
                CnN.DailySlave3 = CnN.DailyBags - 60
            }
            if ( (CnN.DailyBags >= 90) and (CnN.DailyBags > 0)){
                CnN.DailySlave3 = 30.00
            }




        }



    }
}