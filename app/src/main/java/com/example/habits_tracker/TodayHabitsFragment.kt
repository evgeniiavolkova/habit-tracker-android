package com.example.habits_tracker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.architecture.BaseFragment
import com.example.habits_tracker.databinding.FragmentTodayHabitsBinding
import com.example.habits_tracker.databinding.OneItemViewBinding
import com.example.habits_tracker.databinding.RowDoTodayBinding
import com.example.habits_tracker.viewModels.TodayHabitsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalField
import java.time.temporal.WeekFields
import java.util.*
import kotlin.math.log

class TodayHabitsFragment : BaseFragment<FragmentTodayHabitsBinding, TodayHabitsViewModel>(TodayHabitsViewModel::class) {

    private val habitsList: MutableList<Habit> = mutableListOf()
    private lateinit var adapter: HabitAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val dayOfWeek = Calendar.getInstance(TimeZone.getTimeZone("UTC")).get(Calendar.DAY_OF_WEEK)
    private val dow = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    inner class HabitDiffUtils(private val oldList: MutableList<Habit>,
                              private val newList: MutableList<Habit>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].title == newList[newItemPosition].title &&
                    oldList[oldItemPosition].description == newList[newItemPosition].description
        }
    }

    inner class HabitAdapter : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>(){

        inner class HabitViewHolder(val binding: RowDoTodayBinding)
            : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
            return HabitViewHolder(
                RowDoTodayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
            val habit = habitsList.get(position)
            holder.binding.title.text = habit.title
            if (habit.isNotificationOn){
                holder.binding.isNotificationOn.visibility = View.VISIBLE
            } else {
                holder.binding.isNotificationOn.visibility = View.INVISIBLE
            }
            var count: Int = 0

            if (habit.mondayRepeat) count++
            if (habit.tuesdayRepeat) count++
            if (habit.wednesdayRepeat) count++
            if (habit.thursdayRepeat) count++
            if (habit.fridayRepeat) count++
            if (habit.saturdayRepeat) count++
            if (habit.sundayRepeat) count++

            Log.d("Mendelu", "Day of week $dow")
            Log.d("Mendelu", "Day of week $dayOfWeek")

            if (count == 7){
                holder.binding.repeats.text = "Everyday"
            } else {
                holder.binding.repeats.text = "$count times "
            }

            holder.binding.root.setOnClickListener {
                val actions = TodayHabitsFragmentDirections.actionTodayHabitsFragmentToCheckFragment()
                actions.id = habitsList.get(holder.adapterPosition).id!!
                findNavController().navigate(actions)
            }
        }
        override fun getItemCount(): Int = habitsList.size

    }
    override val bindingInflater: (LayoutInflater) -> FragmentTodayHabitsBinding
        get() = FragmentTodayHabitsBinding::inflate

    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = HabitAdapter()
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter


        viewModel
            .getList(dow)
            .observe(viewLifecycleOwner, object : Observer<MutableList<Habit>> {
                override fun onChanged(t: MutableList<Habit>?) {
                    val diffCallback = HabitDiffUtils(habitsList, t!!)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter)
                    habitsList.clear()
                    habitsList.addAll(t!!)
                }
            })
    }

    override fun onActivityCreated() {}

}