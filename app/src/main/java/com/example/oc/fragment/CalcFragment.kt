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
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.oc.MainActivity
import com.example.oc.R
import com.example.oc.data.RnN
import com.example.oc.databinding.FragmentCalcBinding
import com.example.oc.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class CalcFragment : Fragment(),  DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentCalcBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textView.text = RnN.Owner
        binding.reset.setOnClickListener {
            resetEditTexts(binding.frameLayout2)
        }

        binding.dateET.setOnClickListener {
            val currentDate = Calendar.getInstance()
            val year = currentDate.get(Calendar.YEAR)
            val month = currentDate.get(Calendar.MONTH)
            val day = currentDate.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
            datePickerDialog.show()
        }

        binding.startTimeCardView.setOnClickListener {
            showTimePickerDialog(binding.startTimeET)
        }

        binding.endTimeCardView.setOnClickListener {
            showTimePickerDialog(binding.endTimeET)
        }


        return view
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // month is zero-based, so add 1 to match the standard month format
        val formattedMonth = month + 1
        val selectedDate = "$dayOfMonth/$formattedMonth/$year"
        binding.dateET.setText(selectedDate)
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

                if (startTime != "" && endTime != "") {
                    val timeDiff = calculateTimeDifference(startTime, endTime) - 7.5
                    if (timeDiff > 0)
                        binding.overTimeHoursTV.text = timeDiff.toString()
                }
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
        binding.dateET.text = null
    }


}