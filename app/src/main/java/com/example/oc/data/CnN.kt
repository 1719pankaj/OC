package com.example.oc.data

class CnN {
    companion object {

        var TotalIncentive: Double = 0.0
        var DailyIncentive: Double = 0.0
        var OtIncentive: Double = 0.0
        
        var H11_12: Double = 0.0
        var H13_14: Double = 0.0
        var H15_16: Double = 0.0
        var H17_18: Double = 0.0
        var H19_20: Double = 0.0
        var HTotal: Double = 0.0

        var L66_99: Double = 0.0
        var L100_132: Double = 0.0
        var L133_165: Double = 0.0
        var L165Av: Double = 0.0
        var LTotal: Double = 0.0

        var OtFourthSlabBags: Double = 0.0
        var OtThirdSlabBags: Double = 0.0
        var OtSecondSlabBags: Double = 0.0
        var OtFirstSlabBags: Double = 0.0
        var OtWithinNorms: Double = 0.0
        var OtAboveNorms: Double = 0.0
        var FourthSlabBags: Double = 0.0
        var ThirdSlabBags: Double = 0.0
        var SecondSlabBags: Double = 0.0
        var FirstSlabBags: Double = 0.0
        var DailyWithinNorms: Double = 0.0
        var DailyAboveNorms: Double = 0.0
        var weekDay: Boolean = false
        var OtHours: Double = 0.0
        var workingHours: Double = 0.0
        var HeadCount: Int = 0
        var WeightmentTotal: Double = 0.0
        var TruckToTruckTotal: Double = 0.0
        var RestackingTotal: Double = 0.0
        var RefillingTotal: Double = 0.0
        var WagonToTruckTotal: Double = 0.0
        var ShedToWagonTotal: Double = 0.0
        var TruckToPlatformTotal: Double = 0.0
        var ShedToTruckTotal: Double = 0.0
        var PlatformToShedTotal: Double = 0.0
        var WagonToPlatformTotal: Double = 0.0
        var WagonToShedTotal: Double = 0.0
        var TruckToShedTotal: Double = 0.0

        var TotalBags: Double = 0.0
        var PerHeadBags: Double = 0.0

        var OtHourBags: Double = 0.0
        var DailyBags: Double = 0.0

        
        fun resetCnN() {
            DailyBags = 0.0
            OtHourBags = 0.0

            TotalIncentive = 0.0
            DailyIncentive = 0.0
            OtIncentive = 0.0

            OtFourthSlabBags = 0.0
            OtThirdSlabBags = 0.0
            OtSecondSlabBags = 0.0
            OtFirstSlabBags = 0.0
            OtWithinNorms = 0.0
            OtAboveNorms = 0.0
            FourthSlabBags = 0.0
            ThirdSlabBags = 0.0
            SecondSlabBags = 0.0
            FirstSlabBags = 0.0

            DailyWithinNorms = 0.0
            DailyAboveNorms = 0.0
            OtWithinNorms = 0.0
            OtAboveNorms = 0.0

            TotalBags = 0.0
            PerHeadBags = 0.0

            HTotal = 0.0
            LTotal = 0.0

        }
    }
}