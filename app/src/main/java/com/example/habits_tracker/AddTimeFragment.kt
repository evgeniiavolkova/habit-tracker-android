package com.example.habits_tracker

import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.habits_tracker.architecture.BaseFragment
import com.example.habits_tracker.databinding.FragmentAddTimeBinding
import com.example.habits_tracker.utils.DateUtils
import com.example.habits_tracker.viewModels.AddTimeViewModel
import kotlinx.coroutines.launch
import java.util.*


class AddTimeFragment :
    BaseFragment<FragmentAddTimeBinding, AddTimeViewModel>(AddTimeViewModel::class) {

    private val defDate: Calendar = Calendar.getInstance()

    override val bindingInflater: (LayoutInflater) -> FragmentAddTimeBinding
        get() = FragmentAddTimeBinding::inflate

    override fun initViews() {
        fillLayout()
        setInteractionListeners()
    }

    override fun onActivityCreated() {}

    private fun setInteractionListeners() {
        binding.timeText.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            val h = calendar.get(Calendar.HOUR)
            val m = calendar.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(
                requireContext(),
                object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                        viewModel.time = DateUtils.getTime(p1, p2)
                        binding.timeText.text =
                            viewModel.time?.let { it1 ->
                                DateUtils.getTimeString(it1)
                            }
                    }
                },
                h,
                m,
                true
            )
            timePicker.show()

        }
        binding.backButton.setOnClickListener {
            finishCurrentFragment()
        }
        binding.addTimeButton.setOnClickListener {

            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "time",
                viewModel.time
            )
            finishCurrentFragment()

        }
    }

    private fun fillLayout() {
        defDate.set(2022, 1, 1, 1, 1)
        if (viewModel.time != null) {
            binding.timeText.text = DateUtils.getTimeString(viewModel.time!!)
        } else {
            binding.timeText.text = "Not set"
        }
    }
}