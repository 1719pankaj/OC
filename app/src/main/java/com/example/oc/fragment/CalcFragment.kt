package com.example.oc.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.oc.MainActivity
import com.example.oc.calculators.EquivalentBags
import com.example.oc.calculators.HeightCalc
import com.example.oc.calculators.LeadCalc
import com.example.oc.calculators.OcCalc
import com.example.oc.calculators.SlabCalc
import com.example.oc.data.CnN
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalcFragment : Fragment(),  DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentCalcBinding? = null

    private val binding get() = _binding!!

    lateinit var imm: InputMethodManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val view = binding.root

        imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        //Heavy shit, might break
        RnN.calculateDerivedValues()


        binding.reset.setOnClickListener {
            Toast.makeText(context, "reset", Toast.LENGTH_SHORT).show()
            resetEditTexts(binding.frameLayout2)
            resetOutputs()
            CnN.resetCnN()
            setDefaults()
            setWeekdayCheck()
            autoFocusHead()
        }



        binding.dateET.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
            datePickerDialog.show()
        }



        binding.startTimeET.setOnClickListener {
            showTimePickerDialog(binding.startTimeET)
        }



        binding.endTimeET.setOnClickListener {
            showTimePickerDialog(binding.endTimeET)
        }



        binding.weekdaySwitch.setOnCheckedChangeListener { compoundButton, isChecked ->

            CnN.weekDay = isChecked
            val startTime = binding.startTimeET.text.toString()
            val endTime = binding.endTimeET.text.toString()

            if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
                updateOvertime()
            }

//            val switchText = if (CnN.weekDay) "Holiday" else "Workday"
            binding.weekdaySwitch.text = "Holiday"
        }

        setDefaults()


        //add text changed listener to all edit texts
        //if any of them is empty, set overtime to 0
        //else, calculate overtime

        //Truck to Shed
        binding.truckToShedTV.addTextChangedListener {
            EquivalentBags.truckToShedCalc(binding)
            setResultViewVisibility(false)
        }
        binding.truckToShedOowTV.addTextChangedListener {
            EquivalentBags.truckToShedCalc(binding)
            setResultViewVisibility(false)
        }

        //Wagon to Shed
        binding.wagonToShedTV.addTextChangedListener {
            EquivalentBags.wagonToShedCalc(binding)
            setResultViewVisibility(false)

        }
        binding.wagonToShedOowTV.addTextChangedListener {
            EquivalentBags.wagonToShedCalc(binding)
            setResultViewVisibility(false)
        }

        //Wagon to Platform
        binding.wagonToPlatformTV.addTextChangedListener {
            EquivalentBags.wagonToPlatformCalc(binding)
            setResultViewVisibility(false)
        }
        binding.wagonToPlatformOowTV.addTextChangedListener {
            EquivalentBags.wagonToPlatformCalc(binding)
            setResultViewVisibility(false)
        }

        //Platform to Shed
        binding.platformToShedTV.addTextChangedListener {
            EquivalentBags.platformToShedCalc(binding)
            setResultViewVisibility(false)
        }
        binding.platformToShedOowTV.addTextChangedListener {
            EquivalentBags.platformToShedCalc(binding)
            setResultViewVisibility(false)
        }

        //Shed to Truck
        binding.shedToTruckTV.addTextChangedListener {
            EquivalentBags.shedToTruckCalc(binding)
            setResultViewVisibility(false)
        }
        binding.shedToTruckOowTV.addTextChangedListener {
            EquivalentBags.shedToTruckCalc(binding)
            setResultViewVisibility(false)
        }

        //Truck to Platform
        binding.truckToPlatformTV.addTextChangedListener {
            EquivalentBags.truckToPlatformCalc(binding)
            setResultViewVisibility(false)
        }
        binding.truckToPlatformOowTV.addTextChangedListener {
            EquivalentBags.truckToPlatformCalc(binding)
            setResultViewVisibility(false)
        }

        //Shed to Wagon
        binding.shedToWagonTV.addTextChangedListener {
            EquivalentBags.shedToWagonCalc(binding)
            setResultViewVisibility(false)
        }
        binding.shedToWagonOowTV.addTextChangedListener {
            EquivalentBags.shedToWagonCalc(binding)
            setResultViewVisibility(false)
        }

        //Wagon to Truck
        binding.wagonToTruckTV.addTextChangedListener {
            EquivalentBags.wagonToTruckCalc(binding)
            setResultViewVisibility(false)
        }
        binding.wagonToTruckOowTV.addTextChangedListener {
            EquivalentBags.wagonToTruckCalc(binding)
            setResultViewVisibility(false)
        }

        //Refilling
        binding.refillingTV.addTextChangedListener {
            EquivalentBags.refillingCalc(binding)
            setResultViewVisibility(false)
        }

        //Restacking
        binding.restackingTV.addTextChangedListener {
            EquivalentBags.restackingCalc(binding)
            setResultViewVisibility(false)
        }
        binding.restackingOowTV.addTextChangedListener {
            EquivalentBags.restackingCalc(binding)
            setResultViewVisibility(false)
        }

        //Weightment
        binding.weightmentTV.addTextChangedListener {
            EquivalentBags.weightmentCalc(binding)
            setResultViewVisibility(false)
        }

        //Truck to Truck
        binding.truckToTruckTV.addTextChangedListener {
            EquivalentBags.truckToTruckCalc(binding)
            setResultViewVisibility(false)
        }
        binding.truckToTruckOowTV.addTextChangedListener {
            EquivalentBags.truckToTruckCalc(binding)
            setResultViewVisibility(false)
        }


        binding.headCountET.addTextChangedListener {
            EquivalentBags.updateHeadCount(binding)
            setResultViewVisibility(false)
        }

        binding.h1112ET.addTextChangedListener {
            CnN.H11_12 = binding.h1112ET.text.toString().toDoubleOrNull()?: 0.0
            HeightCalc.heightTotal(binding)

            setResultViewVisibility(false)
        }

        binding.h1314ET.addTextChangedListener {
            CnN.H13_14 = binding.h1314ET.text.toString().toDoubleOrNull()?: 0.0
            HeightCalc.heightTotal(binding)

            setResultViewVisibility(false)
        }

        binding.h1516ET.addTextChangedListener {
            CnN.H15_16 = binding.h1516ET.text.toString().toDoubleOrNull()?: 0.0
            HeightCalc.heightTotal(binding)

            setResultViewVisibility(false)
        }

        binding.h1718ET.addTextChangedListener {
            CnN.H17_18 = binding.h1718ET.text.toString().toDoubleOrNull()?: 0.0
            HeightCalc.heightTotal(binding)

            setResultViewVisibility(false)
        }

        binding.h1920ET.addTextChangedListener {
            CnN.H19_20 = binding.h1920ET.text.toString().toDoubleOrNull()?: 0.0
            HeightCalc.heightTotal(binding)

            setResultViewVisibility(false)
        }

        binding.l6699ET.addTextChangedListener {
            CnN.L66_99 = binding.l6699ET.text.toString().toDoubleOrNull()?: 0.0
            LeadCalc.leadTotal(binding)

            setResultViewVisibility(false)
        }

        binding.l100132ET.addTextChangedListener {
            CnN.L100_132 = binding.l100132ET.text.toString().toDoubleOrNull()?: 0.0
            LeadCalc.leadTotal(binding)

            setResultViewVisibility(false)
        }

        binding.l133165ET.addTextChangedListener {
            CnN.L133_165 = binding.l133165ET.text.toString().toDoubleOrNull()?: 0.0
            LeadCalc.leadTotal(binding)

            setResultViewVisibility(false)
        }

        binding.l165PlusET.addTextChangedListener {
            CnN.L165Av = binding.l165PlusET.text.toString().toDoubleOrNull()?: 0.0
            LeadCalc.leadTotal(binding)

            setResultViewVisibility(false)
        }

        binding.calculateBT.setOnClickListener {
            EquivalentBags.updateTotalBags(binding)
            SlabCalc(binding)
            LeadCalc.leadTotal(binding)
            HeightCalc.heightTotal(binding)
            MasterCalc(binding)
            CnN.resetCnN()
        }


        return view
    }

    private fun autoFocusHead() {
        binding.headCountET.requestFocus()
        imm.showSoftInput(binding.headCountET, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun MasterCalc(binding: FragmentCalcBinding) {

        if(imm.isActive && CnN.HeadCount > 0){
            imm.hideSoftInputFromWindow(binding.calculateBT.windowToken, 0)
        }

        CnN.DailyIncentive += CnN.HTotal + CnN.LTotal
        CnN.TotalIncentive = CnN.OtIncentive + CnN.DailyIncentive + CnN.OtWithinNorms

        binding.otIncentiveTotalTV.text  = CnN.OtIncentive.roundToTwoDecimalPlaces().toString()
        binding.dailyIncentiveTotalTV.text = CnN.DailyIncentive.roundToTwoDecimalPlaces().toString()
        binding.otTotalTV.text = CnN.OtWithinNorms.roundToTwoDecimalPlaces().toString()

        binding.masterTotalTV.text = "Gross Incentive: ${CnN.TotalIncentive.roundToTwoDecimalPlaces()}"

        binding.masterTotalCardView.visibility = View.VISIBLE

    }

    private fun SlabCalc(binding: FragmentCalcBinding) {

        if (CnN.HeadCount <= 0){
            autoFocusHead()
            return
        }
        SlabCalc.splitDailyBagsOTBags(binding)
    }

    private fun setDefaults() {
        val defaultStartTime = format12Hour(10, 0)
        binding.startTimeET.setText(defaultStartTime)

        val deafultEndTime = format12Hour(17, 30)
        binding.endTimeET.setText(deafultEndTime)

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        binding.dateET.text = currentDate

        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(currentDate)

        binding.weekdaySwitch.isChecked = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        updateOvertime()

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // month is zero-based, so add 1 to match the standard month format Placeholoder
        val formattedMonth = month + 1
        val selectedDate = "$dayOfMonth/$formattedMonth/$year"
        binding.dateET.setText(selectedDate)

        setWeekdayCheck()
    }

    private fun showTimePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = format12Hour(hourOfDay, minute)
                textView.setText(selectedTime)

                val startTime = binding.startTimeET.text.toString()
                val endTime = binding.endTimeET.text.toString()

                if (startTime != "" && endTime != "")
                    updateOvertime()

            },
            currentHour,
            currentMinute,
            false
        )

        timePickerDialog.show()
    }

    private fun format12Hour(hourOfDay: Int, minute: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
    private fun calculateTimeDifference(startTime: String, endTime: String): Float {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val start = sdf.parse(startTime)
        val end = sdf.parse(endTime)

        val diff = end.time - start.time
        val minutes = diff / (1000 * 60)

        return minutes / 60.0f
    }

    private fun updateOvertime() {

        val startTime = binding.startTimeET.text.toString()
        val endTime = binding.endTimeET.text.toString()

        if (CnN.weekDay) {
            val timeDiff = calculateTimeDifference(startTime, endTime) - RnN.lunchHours
//            Toast.makeText(context, "holiday ${startTime + ", " + endTime + ", timediff: " + timeDiff}", Toast.LENGTH_SHORT).show()
            if (timeDiff >= 0) {
                CnN.OtHours = timeDiff
                binding.overTimeHoursTV.text = formatHoursMinutes(CnN.OtHours)
            }
        } else {
            val timeDiff = calculateTimeDifference(startTime, endTime) - (RnN.workingHours + RnN.lunchHours)
//            Toast.makeText(context, "workday ${startTime + ", " + endTime + ", timediff: " + timeDiff}", Toast.LENGTH_SHORT).show()
            if (timeDiff >= 0) {
                CnN.OtHours = timeDiff
                binding.overTimeHoursTV.text = formatHoursMinutes(CnN.OtHours)
            }
        }
    }


    fun formatHoursMinutes(hours: Double): String {
        val wholeHours = hours.toInt()
        val minutes = ((hours - wholeHours) * 60).toInt()

        val decimalFormat = DecimalFormat("00")
        val formattedHours = decimalFormat.format(wholeHours)
        val formattedMinutes = decimalFormat.format(minutes)

        return "$formattedHours:$formattedMinutes"
    }




    fun resetEditTexts(layout: ConstraintLayout) {
        for (i in 0 until layout.childCount) {
            val view = layout.getChildAt(i)
            if (view is CardView) {
                val cardView = view as CardView
                for (j in 0 until cardView.childCount) {
                    val childView = cardView.getChildAt(j)
                    if (childView is EditText) {
                        val editText = childView as EditText
                        editText.text = null
                    }
                }
            }
        }

    }

    private fun resetOutputs() {
        binding.masterTotalTV.text = 0.0.toString()
        setResultViewVisibility(false)

        binding.heightTotalTV.text = 0.0.toString()
        binding.leadTotalTV.text = 0.0.toString()
        binding.dailyIncentiveTotalTV.text = 0.0.toString()
        binding.otIncentiveTotalTV.text = 0.0.toString()
        binding.otTotalTV.text = 0.0.toString()
    }

    fun setResultViewVisibility(boolean: Boolean) {
        if (boolean) {
            binding.masterTotalCardView.visibility = View.VISIBLE
            return
        }
        binding.masterTotalCardView.visibility = View.GONE
    }

    fun setWeekdayCheck() {

        val date = binding.dateET.text

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(date.toString()) as Date

        if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            binding.weekdaySwitch.setChecked(true)
            updateOvertime()
        } else {
            binding.weekdaySwitch.setChecked(false)
            updateOvertime()
        }
    }


    fun Double.roundToTwoDecimalPlaces(): Double {
        return String.format("%.2f", this).toDouble()
    }




}