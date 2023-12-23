package com.example.oc.data

class RnN {
    companion object {
        var Owner: String = "MTLI"
        var CurrentVersion: String = "1.0.02rc"
        var MaalkiKaNumber: String = "9883293901"

        var workingHours: Double = 7.0
        var lunchHours: Double = 0.5

        var DailyNorms: Int = 135
        var TruckToShed: Int = 135
        var WagonToShed: Int = 115
        var WagonToPlatform: Int = 170
        var PlatformToShed: Int = 180
        var ShedToTruck: Int = 140
        var TruckToPlatform: Int = 170
        var ShedToWagon: Int = 120
        var WagonToTruck: Int = 110
        var Refilling: Int = 55
        var Restacking: Int = 180
        var Weightment: Int = 105
        var TruckToTruck: Int = 140

        var OTNomrs: Int = 19

        var OldBasic: Double = 30600.0
        var OldDA: Double = 92.9
        var OldHRA: Double = 30.0
        var Days: Int = 365

        var NewBasic: Double = 48100.0
        var NewDA: Double = 37.9
        var NewHRA: Double = 27.0

        var perDayBasic: Double = 0.0
        var perDayDA: Double = 0.0
        var perDayHRA: Double = 0.0
        var perDayTotal: Double = 0.0
        var perDayBags: Double = 0.0

        var perDayBasicNew: Double = 0.0
        var perDayDANew: Double = 0.0
        var perDayHRANew: Double = 0.0
        var perDayTotalNew: Double = 0.0
        var perHourTotal: Double = 0.0

        var H11_12: Double = 0.0
        var H13_14: Double = 0.0
        var H15_16: Double = 0.0
        var H17_18: Double = 0.0
        var H19_20: Double = 0.0

        var L66_99: Double = 0.0
        var L100_132: Double = 0.0
        var L133_165: Double = 0.0
        var L165Av: Double = 0.0

        var firstSlab: Double = 0.0
        var secondSlab: Double = 0.0
        var thirdSlab: Double = 0.0
        var fourthSlab: Double = 0.0


        fun calculateDerivedValues() {
            perDayBasic = (OldBasic / 26)
            perDayDA = ((OldBasic * (OldDA / 100) * 12) / Days)
            perDayHRA = ((OldBasic * (OldHRA / 100) * 12) / Days)
            perDayTotal = (perDayBasic + perDayDA + perDayHRA)
            perDayBags = (perDayTotal / TruckToShed)
            perDayBasicNew = (NewBasic / 26)
            perDayDANew = ((NewBasic * (NewDA / 100) * 12) / Days)
            perDayHRANew = ((NewBasic * (NewHRA / 100) * 12) / Days)
            perDayTotalNew = (perDayBasicNew + perDayDANew + perDayHRANew)
            perHourTotal = ((perDayTotalNew / workingHours)*1.1)

            H11_12 = perDayBags * 0.1
            H13_14 = perDayBags * 0.25
            H15_16 = perDayBags * 0.3
            H17_18 = perDayBags * 0.4
            H19_20 = perDayBags * 0.5

            L66_99 = perDayBags * 0.15
            L100_132 = perDayBags * 0.3
            L133_165 = perDayBags * 0.5
            L165Av = perDayBags

            firstSlab = perDayBags * 1.08
            secondSlab = perDayBags * 1.15
            thirdSlab = perDayBags * 1.35
            fourthSlab = perDayBags * 1.50
        }
    }
}