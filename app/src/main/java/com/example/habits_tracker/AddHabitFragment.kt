package com.example.habits_tracker

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.CompoundButton
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habits_tracker.utils.DateUtils
import com.example.habits_tracker.architecture.BaseFragment
import com.example.habits_tracker.databinding.FragmentAddHabitBinding
import com.example.habits_tracker.viewModels.AddHabitViewModel
import io.karn.notify.Notify
import kotlinx.coroutines.launch
import java.util.*


class AddHabitFragment :
    BaseFragment<FragmentAddHabitBinding, AddHabitViewModel>(AddHabitViewModel::class) {

    private val arguments: AddHabitFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentAddHabitBinding
        get() = FragmentAddHabitBinding::inflate

    override fun initViews() {
        setHasOptionsMenu(true)

        viewModel.id = if (arguments.id != -1L) arguments.id else null

        if (viewModel.id == null) {
            fillLayout()
        } else {
            lifecycleScope.launch {
                viewModel.habit = viewModel.findById()
            }.invokeOnCompletion {
                fillLayout()
            }
        }

        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Long>("time")
            ?.observe(viewLifecycleOwner) {
                viewModel.habit.timeRemainder = it
            }

        setStartDate()
        setFinishDate()
        setInteractionListeners()
        setRepeats()
        binding.isNotificationOn.isChecked = viewModel.habit.isNotificationOn

        if (viewModel.habit.isNotificationOn){
            if (viewModel.habit.timeRemainder != null){
                //
            }
        }

        if (viewModel.habit.timeRemainder != null){
            binding.clock.visibility = View.VISIBLE
            binding.clock.text = DateUtils.getTimeString(viewModel.habit.timeRemainder!!)

        } else {
            binding.clock.visibility = View.GONE

        }

    }

    override fun onActivityCreated() {}

    private fun fillLayout() {
        if (viewModel.habit.title.isNotEmpty()) {
            binding.habitTitle.title_text = viewModel.habit.title

        }
        if (!viewModel.habit.description.isEmpty()) {
            binding.habitDescription.description_text = viewModel.habit.description
        }

        setStartDate()
        setFinishDate()
        setRepeats()
        binding.isNotificationOn.isChecked = viewModel.habit.isNotificationOn

        if (binding.isNotificationOn.isChecked) {
            binding.textIsNotificationOn.visibility = View.VISIBLE
            binding.textIsNotificationOn.text = viewModel.habit.notificationText.toString()
        }

    }

    private fun setInteractionListeners() {

        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteHabit()

            }.invokeOnCompletion {
                finishCurrentFragment()
            }
        }

        binding.timeNotificationButton.setOnClickListener {
            findNavController().navigate(AddHabitFragmentDirections.actionAddHabitFragmentToAddTimeFragment())

        }

        binding.startDate.setOnClickListener {
            setDialogForStart()
        }
        binding.finishDate.setOnClickListener {
            setDialogForFinish()
        }
        binding.habitTitle.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.habit.title = p0.toString()
            }
        })
        binding.habitDescription.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.habit.description = p0.toString()
            }
        })
        binding.textIsNotificationOn.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.habit.notificationText = p0.toString()
            }
        })
        binding.isNotificationOn.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                if (p1) {
                    binding.textIsNotificationOn.visibility = View.VISIBLE
                    viewModel.habit.isNotificationOn = true
                } else {
                    binding.textIsNotificationOn.visibility = View.GONE
                    viewModel.habit.isNotificationOn = false
                }
            }
        })
        checkedDayRepeat()
        binding.saveButton.setOnClickListener {
            if (binding.habitTitle.title_text.isNotEmpty()
                && binding.habitDescription.description_text.isNotEmpty()
            ) {
                if (binding.isNotificationOn.isChecked) {
                    if (binding.textIsNotificationOn.text.isNotEmpty()) {
                        binding.habitTitle.setError(null)
                        binding.habitDescription.setError(null)
                        binding.textIsNotificationOn.setError(null)
                        lifecycleScope.launch {
                            viewModel.saveHabit()
                            finishCurrentFragment()
                        }
                    } else {
                        binding.textIsNotificationOn.setError("Cannot be empty")
                    }
                } else {
                    binding.habitTitle.setError(null)
                    binding.habitDescription.setError(null)
                    binding.textIsNotificationOn.setError(null)
                    lifecycleScope.launch {
                        viewModel.saveHabit()
                        finishCurrentFragment()
                    }
                }
            } else {
                binding.habitTitle.setError("Cannot be empty")
                binding.habitDescription.setError("Cannot be empty")
                binding.textIsNotificationOn.setError("Cannot be empty")
            }
        }
    }

    private fun setDialogForStart() {

        val calendar: Calendar = Calendar.getInstance()
        viewModel.habit.startDate?.let {
            calendar.timeInMillis = it
        }
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(
            requireContext(),
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    viewModel.habit.startDate = DateUtils.getUnixTime(year, month, day)
                    setStartDate()
                }
            },
            y,
            m,
            d
        )

        dialog.datePicker.minDate = calendar.timeInMillis

        dialog.show()

    }

    private fun setStartDate() {
        viewModel.habit.startDate?.let {
            binding.startDate.hint = DateUtils.getDateString(it)
        } ?: kotlin.run {
            binding.startDate.hint = "Not set"
        }
    }

    private fun setFinishDate() {
        viewModel.habit.finishDate?.let {
            binding.finishDate.hint = DateUtils.getDateString(it)
        } ?: kotlin.run {
            binding.finishDate.hint = "Not set"
        }
    }

    private fun setDialogForFinish() {
        val calendar: Calendar = Calendar.getInstance()
        viewModel.habit.finishDate?.let {
            calendar.timeInMillis = it
        }
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            requireContext(),
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    viewModel.habit.finishDate = DateUtils.getUnixTime(year, month, day)
                    setFinishDate()
                }
            },
            y,
            m,
            d
        )
        dialog.show()
    }

    private fun setRepeats() {
        binding.monday.isChecked = viewModel.habit.mondayRepeat
        binding.tuesday.isChecked = viewModel.habit.tuesdayRepeat
        binding.wednesday.isChecked = viewModel.habit.wednesdayRepeat
        binding.thursday.isChecked = viewModel.habit.thursdayRepeat
        binding.friday.isChecked = viewModel.habit.fridayRepeat
        binding.saturday.isChecked = viewModel.habit.saturdayRepeat
        binding.sunday.isChecked = viewModel.habit.sundayRepeat
    }

    private fun checkedDayRepeat() {
        binding.monday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.mondayRepeat = p1
            }
        })
        binding.tuesday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.tuesdayRepeat = p1
            }
        })
        binding.wednesday.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.wednesdayRepeat = p1
            }
        })
        binding.thursday.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.thursdayRepeat = p1
            }
        })
        binding.friday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.fridayRepeat = p1
            }
        })
        binding.saturday.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.saturdayRepeat = p1
            }
        })
        binding.sunday.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                viewModel.habit.sundayRepeat = p1
            }
        })
    }
}



