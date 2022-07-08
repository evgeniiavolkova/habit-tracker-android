package com.example.habits_tracker

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.habits_tracker.Model.ActivityHistory
import com.example.habits_tracker.architecture.BaseFragment
import com.example.habits_tracker.databinding.FragmentCheckBinding
import com.example.habits_tracker.utils.DateUtils
import com.example.habits_tracker.viewModels.CheckViewModel
import kotlinx.coroutines.launch
import java.util.*

class CheckFragment : BaseFragment<FragmentCheckBinding, CheckViewModel>(CheckViewModel::class) {
    private val arguments: CheckFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater) -> FragmentCheckBinding
        get() = FragmentCheckBinding::inflate

    override fun initViews() {
        viewModel.habitId = if (arguments.id != -1L) arguments.id else null

        if (viewModel.habitId == null) {
            fillLayout()
        } else {
            lifecycleScope.launch {
                    viewModel.habit = viewModel.findHabitByHabitId()
                    viewModel.activityHistory.title = viewModel.habit.title
                    viewModel.activityHistory.description = viewModel.habit.description
                    viewModel.activityHistory.habitId = viewModel.habitId!!

            }.invokeOnCompletion {
                fillLayout()
            }
        }

        // perfect days
        /*if (viewModel.habitId != null) {
            var count: Int = 0
            var lastActivityHistory = ActivityHistory(0, "",0, 0,"")
            var lastActionDate :Long? = 0
            lifecycleScope.launchWhenCreated {
                count = viewModel.getCountHabitsWithId()
            }.invokeOnCompletion {
                if (count != 0) {
                    lifecycleScope.launchWhenCreated {
                        lastActivityHistory = viewModel.findByHabitId()
                        lastActionDate = lastActivityHistory.createdDate
                    }.invokeOnCompletion {
                        val lastDateString = lastActionDate?.let { it1 ->
                            DateUtils.getDateString(
                                it1
                            )
                        }
                        val today = Calendar.getInstance().timeInMillis
                        val todayString = DateUtils.getDateString(today)

                        if (todayString == lastDateString) {
                            binding.checkButton.isEnabled = false
                            binding.checkButton.setBackgroundColor(resources.getColor(R.color.gray))
                        }
                    }
                }
            }
        } */




        binding.statistic.setOnClickListener{
            val actions = CheckFragmentDirections.actionCheckFragmentToStatisticsFragment()
            actions.id = viewModel.habitId!!
            findNavController().navigate(actions)
        }

        binding.checkButton.setOnClickListener {
            viewModel.activityHistory.count++
            var counter = 0
            val today = Calendar.getInstance()
            viewModel.activityHistory.createdDate = today.timeInMillis
            viewModel.activityHistory.dayOfWeek = today.get(Calendar.DAY_OF_WEEK)
            lifecycleScope.launch {
                viewModel.saveActivituEntery()
            }.invokeOnCompletion {
                counter++
                binding.statistic.visibility = View.VISIBLE
                binding.checkButton.isEnabled = false
            }
        }
        binding.deleteButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteHabit()
                viewModel.deleteFromActivityHistory()
            }.invokeOnCompletion {
                finishCurrentFragment()
            }
        }
        binding.rewriteButton.setOnClickListener {
            val actions = CheckFragmentDirections.actionCheckFragmentToAddHabitFragment()
            actions.id = viewModel.habitId!!
            findNavController().navigate(actions)
        }
    }
    private fun fillLayout(){
        binding.habitTitle.text = viewModel.activityHistory.title
        binding.habitDescription.text = viewModel.activityHistory.description
        if (!viewModel.habit.notificationText.isNullOrBlank()){
            binding.note.text = viewModel.habit.notificationText
        } else {
            binding.note.text = "Note is empty"
        }

    }

    override fun onActivityCreated() {}


}