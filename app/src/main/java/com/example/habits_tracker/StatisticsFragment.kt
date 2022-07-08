package com.example.habits_tracker

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.habits_tracker.architecture.BaseFragment
import com.example.habits_tracker.databinding.CalendarHeaderBinding
import com.example.habits_tracker.databinding.FragmentStatisticsBinding
import com.example.habits_tracker.utils.DateUtils
import com.example.habits_tracker.viewModels.StatisticViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.yearMonth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.*


class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, StatisticViewModel>(StatisticViewModel::class){
    private val arguments: StatisticsFragmentArgs by navArgs()

    private val today = LocalDate.now()
    private var selectedDate: LocalDate? = null
    private val headerDateFormatter = DateTimeFormatter.ofPattern("EEE'\n'd MMM")

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    private val startBackground: GradientDrawable by lazy {
        requireContext().getDrawableCompat(R.drawable.continious_selected_start_bg) as GradientDrawable
    }
    private val endBackground: GradientDrawable by lazy {
        requireContext().getDrawableCompat(R.drawable.continious_selected_end_bg) as GradientDrawable
    }

    override val bindingInflater: (LayoutInflater) -> FragmentStatisticsBinding
        get() = FragmentStatisticsBinding::inflate

    override fun initViews() {

        viewModel.habitId = if (arguments.id != -1L) arguments.id else null

        if (viewModel.habitId == null) {
            //
            Log.d("Mendelu", "0")
        } else {
            lifecycleScope.launch {
                viewModel.habit = viewModel.findHabitByHabitId()

            }.invokeOnCompletion {
                if (viewModel.habit.startDate != null){
                    val formatter = DateTimeFormatter.ofPattern("d/MM/yyyy")
                    val dateString = DateUtils.getDataStringForStatictics(viewModel.habit.startDate!!)
                    val result = LocalDate.parse(dateString, formatter)
                    startDate = result
                }
                if (viewModel.habit.finishDate != null){
                    val formatter = DateTimeFormatter.ofPattern("d/MM/yyyy")
                    val dateString = DateUtils.getDataStringForStatictics(viewModel.habit.finishDate!!)
                    val result = LocalDate.parse(dateString, formatter)
                    endDate = result
                }
                lifecycleScope.launch {
                    viewModel.activityHistory = viewModel.findActivityByHabitId()
                }.invokeOnCompletion {
                    fillLayout()
                    logic()
                }
            }
        }


    }

    private fun logic(){

        binding.exFourCalendar.post {
            val radius = ((binding.exFourCalendar.width / 7) / 2).toFloat()
            startBackground.setCornerRadius(topLeft = radius, bottomLeft = radius)
            endBackground.setCornerRadius(topRight = radius, bottomRight = radius)
        }
        val daysOfWeek = daysOfWeekFromLocale()
        binding.legendLayout.root.children.forEachIndexed { index, view ->
            (view as TextView).apply {
                text = daysOfWeek[index].getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                setTextColorRes(R.color.gray)
            }
        }
        val currentMonth = YearMonth.now()
        binding.exFourCalendar.setup(currentMonth, currentMonth.plusMonths(12), daysOfWeek.first())
        binding.exFourCalendar.scrollToMonth(currentMonth)

        binding.exFourCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exFourDayText
                val roundBgView = container.binding.exFourRoundBgView

                textView.text = null
                textView.background = null
                roundBgView.makeInVisible()

                val startDate = startDate
                val endDate = endDate



                when (day.owner) {
                    DayOwner.THIS_MONTH -> {
                        textView.text = day.day.toString()
                        if (day.date.isBefore(today)) {
                            textView.setTextColorRes(R.color.gray)
                        } else {
                            when {
                                startDate == day.date && endDate == null -> {
                                    textView.setTextColorRes(R.color.white)
                                    roundBgView.makeVisible()
                                    roundBgView.setBackgroundResource(R.drawable.single_selected_bg)
                                }
                                day.date == startDate -> {
                                    textView.setTextColorRes(R.color.white)
                                    textView.background = startBackground
                                }
                                startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                                    textView.setTextColorRes(R.color.white)
                                    textView.setBackgroundResource(R.drawable.continious_selected_midle_bg)
                                }
                                day.date == endDate -> {
                                    textView.setTextColorRes(R.color.white)
                                    textView.background = endBackground
                                }
                                day.date == today -> {
                                    textView.setTextColorRes(R.color.primaryColor)
                                    roundBgView.makeVisible()
                                    roundBgView.setBackgroundResource(R.drawable.today_bg)
                                }
                                else -> textView.setTextColorRes(R.color.gray)
                            }
                        }
                    }
                    // Make the coloured selection background continuous on the invisible in and out dates across various months.
                    DayOwner.PREVIOUS_MONTH ->
                        if (startDate != null && endDate != null && isInDateBetween(day.date, startDate, endDate)) {
                            textView.setBackgroundResource(R.drawable.continious_selected_midle_bg)
                        }
                    DayOwner.NEXT_MONTH ->
                        if (startDate != null && endDate != null && isOutDateBetween(day.date, startDate, endDate)) {
                            textView.setBackgroundResource(R.drawable.continious_selected_midle_bg)
                        }
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarHeaderBinding.bind(view).exFourHeaderText
        }
        binding.exFourCalendar.monthHeaderBinder = object :
            MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                val monthTitle = "${month.yearMonth.month.name.lowercase(Locale.getDefault())
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }} ${month.year}"
                container.textView.text = monthTitle
            }
        }

        bindSummaryViews()
    }

    override fun onActivityCreated() {}

    private fun isInDateBetween(inDate: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (inDate.yearMonth == startDate.yearMonth) return true
        val firstDateInThisMonth = inDate.plusMonths(1).yearMonth.atDay(1)
        return firstDateInThisMonth >= startDate && firstDateInThisMonth <= endDate && startDate != firstDateInThisMonth
    }
    private fun isOutDateBetween(outDate: LocalDate, startDate: LocalDate, endDate: LocalDate): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (outDate.yearMonth == endDate.yearMonth) return true
        val lastDateInThisMonth = outDate.minusMonths(1).yearMonth.atEndOfMonth()
        return lastDateInThisMonth >= startDate && lastDateInThisMonth <= endDate && endDate != lastDateInThisMonth
    }

    private fun bindSummaryViews() {
        binding.exFourStartDateText.apply {
            if (startDate != null) {
                text = headerDateFormatter.format(startDate)
                setTextColorRes(R.color.gray)
            } else {
                text = getString(R.string.start)
                setTextColor(Color.GRAY)
            }
        }

        binding.exFourEndDateText.apply {
            if (endDate != null) {
                text = headerDateFormatter.format(endDate)
                setTextColorRes(R.color.gray)
            } else {
                text = getString(R.string.start)
                setTextColor(Color.GRAY)
            }
        }
    }
    private fun fillLayout(){
        val today: LocalDate = LocalDate.now()
        val daysBetween: Long = ChronoUnit.DAYS.between(startDate, today)
        binding.daysFromStart.text = daysBetween.toString()

        if (daysBetween.toInt() <= 0 && viewModel.activityHistory.count > 0 ){
            if (daysBetween.toInt() != 0){
                val day = daysBetween * (-1)
                val avg: Float = (viewModel.activityHistory.count.toFloat() / day.toFloat())*(100)
                binding.average.text = avg.toString()
            } else {
                val day = 1
                val avg: Float = (viewModel.activityHistory.count.toFloat() / day.toFloat())*(100)
                binding.average.text = avg.toString()
            }
        } else {
            val coubt = viewModel.activityHistory.count
            val avg: Float = (viewModel.activityHistory.count.toFloat() / daysBetween.toFloat())*100
            binding.average.text = "$avg %"
        }
        if (daysBetween.toInt() != 0){
            if (viewModel.activityHistory.count < daysBetween.toInt()){
                val unmet = daysBetween - viewModel.activityHistory.count
                binding.unmet.text = unmet.toString()
            } else {
                binding.unmet.text = "0"
            }
        }

    }

}
