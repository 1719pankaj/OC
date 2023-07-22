package com.example.oc.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.oc.MainActivity
import com.example.oc.R
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding
import com.example.oc.databinding.FragmentMainBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalcFragment : Fragment(),  DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentCalcBinding? = null

    private val binding get() = _binding!!

    private var overTimeHours = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textView.text = RnN.Owner

        binding.reset.setOnClickListener {
            Toast.makeText(context, "reset", Toast.LENGTH_SHORT).show()
            resetEditTexts(binding.frameLayout2)
            setDefaults()
            setWeekdayCheck()
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
            val startTime = binding.startTimeET.text.toString()
            val endTime = binding.endTimeET.text.toString()

            if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
                updateOvertime()
            }

            val switchText = if (isChecked) "Holiday" else "Workday"
            binding.weekdaySwitch.text = switchText
        }



        setDefaults()

        return view
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

        if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            binding.weekdaySwitch.setChecked(true)
        }
        updateOvertime()

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // month is zero-based, so add 1 to match the standard month format
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

        if (binding.weekdaySwitch.isChecked) {
            val timeDiff = calculateTimeDifference(startTime, endTime) - RnN.lunchHours
//            Toast.makeText(context, "holiday ${startTime + ", " + endTime + ", timediff: " + timeDiff}", Toast.LENGTH_SHORT).show()
            if (timeDiff >= 0) {
                overTimeHours = timeDiff
                updateOverTimeInUI(timeDiff)
            }
        } else {
            val timeDiff = calculateTimeDifference(startTime, endTime) - (RnN.workingHours + RnN.lunchHours)
//            Toast.makeText(context, "workday ${startTime + ", " + endTime + ", timediff: " + timeDiff}", Toast.LENGTH_SHORT).show()
            if (timeDiff >= 0) {
                overTimeHours = timeDiff
                updateOverTimeInUI(timeDiff)
            }

        }
    }



    fun updateOverTimeInUI(overTime: Double) {
        val formattedOverTime = formatHoursMinutes(overTime)
        binding.overTimeHoursTV.text = formattedOverTime
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




}