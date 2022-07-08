package com.example.habits_tracker

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habits_tracker.Model.Habit
import com.example.habits_tracker.architecture.BaseFragment
import com.example.habits_tracker.databinding.FragmentMainBinding
import com.example.habits_tracker.databinding.OneItemViewBinding
import com.example.habits_tracker.viewModels.MainViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(MainViewModel::class) {

    private val habitList: MutableList<Habit> = mutableListOf()
    private lateinit var adapter: HabitAdapter
    private lateinit var layoutManager: LinearLayoutManager


    inner class DiffUtils(private val oldList: MutableList<Habit>,
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

        inner class HabitViewHolder(val binding: OneItemViewBinding)
            : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
            return HabitViewHolder(
                OneItemViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
            val oneHabit = habitList.get(position)
            holder.binding.habitTitle.text = oneHabit.title
            holder.binding.habitDescription.text = oneHabit.description


            if (oneHabit.startDate != null){
                val setStart = com.example.habits_tracker.utils.DateUtils.getDateString(oneHabit.startDate!!)
                holder.binding.habitStart.text = setStart
            } else {
                holder.binding.habitStart.text = "Not set"
            }

            if (oneHabit.finishDate != null){
                val setFinish = com.example.habits_tracker.utils.DateUtils.getDateString(oneHabit.finishDate!!)
                holder.binding.habitFinish.text = setFinish
                holder.binding.finish.visibility = View.VISIBLE
            } else {
                holder.binding.finish.visibility = View.GONE
            }

            holder.binding.root.setOnClickListener {
                val actions = MainFragmentDirections.actionMainFragmentToAddHabitFragment()
                actions.id = habitList.get(holder.adapterPosition).id!!
                findNavController().navigate(actions)
            }
        }
        override fun getItemCount(): Int = habitList.size

    }
    override val bindingInflater: (LayoutInflater) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initViews() {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = HabitAdapter()
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter

        viewModel
            .getAll()
            .observe(viewLifecycleOwner, object : Observer<MutableList<Habit>> {
                override fun onChanged(t: MutableList<Habit>?) {
                    val diffCallback = DiffUtils(habitList, t!!)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)
                    diffResult.dispatchUpdatesTo(adapter)
                    habitList.clear()
                    habitList.addAll(t!!)
                }

            })

        binding.vers.text = "Version: "+ BuildConfig.VERSION_NAME
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d")
        val current = LocalDateTime.now()
        val formatted = current.format(formatter)
        binding.dateText.text = formatted.toString()

        binding.addButton.setOnClickListener{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddHabitFragment())
        }

    }

    override fun onActivityCreated() {}
}