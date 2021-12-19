package com.lefarmico.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lefarmico.core.R
import com.lefarmico.core.adapter.diffUtil.CalendarDiffCallback
import com.lefarmico.core.databinding.CalendarItemDeselectUncheckedBinding
import com.lefarmico.core.entity.CalendarItemViewData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarAdapter(
    private val currentDate: LocalDate,
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    var clickListener: ((date: LocalDate) -> Unit) = {}

    private var selectedItemIndex = -1
    private var currentDateSelect = true
    private var selectedDate: LocalDate? = null

    var items = mutableListOf<CalendarItemViewData>()
        set(value) {
            val oldField = field
            field = value
            val diffCallback = CalendarDiffCallback(oldField, field)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    class CalendarViewHolder(
        calendarItem: CalendarItemDeselectUncheckedBinding
    ) : RecyclerView.ViewHolder(calendarItem.root) {

        private val weekDayName = calendarItem.weekDay
        private val dayNumber = calendarItem.dayNumber
        private val point = calendarItem.check
        val root = calendarItem.root

        fun bind(calendarItem: CalendarItemViewData) {
            val localDateTime = calendarItem.date
            val formatter = DateTimeFormatter.ofPattern("EEE", Locale.getDefault())
            val dayInWeek = localDateTime.format(formatter)

            weekDayName.text = dayInWeek
            dayNumber.text = localDateTime.dayOfMonth.toString()

            when (calendarItem.isChecked) {
                true -> setChecked()
                false -> setUnchecked()
            }
        }
        fun bindSelected() {
            root.background = ContextCompat.getDrawable(root.context, R.drawable.shape_button)
            point.background = ContextCompat.getDrawable(root.context, R.drawable.shape_menu_item_single)
            root.isEnabled = false
        }
        fun bindDeselected() {
            root.background = ContextCompat.getDrawable(root.context, R.drawable.shape_menu_item_single)
            point.background = ContextCompat.getDrawable(root.context, R.drawable.shape_button)
            root.isEnabled = true
        }
        private fun setChecked() {
            point.visibility = View.VISIBLE
        }
        private fun setUnchecked() {
            point.visibility = View.INVISIBLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder =
        CalendarViewHolder(
            CalendarItemDeselectUncheckedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(items[position])
        if (selectedDate != null && selectedDate!!.equals(items[position].date)) {
            holder.bindSelected()
        } else {
            holder.bindDeselected()
        }
        holder.root.setOnClickListener {
            holder.bindSelected()
            notifyItemChanged(selectedItemIndex)
            selectedItemIndex = holder.absoluteAdapterPosition
            selectedDate = items[holder.absoluteAdapterPosition].date
            notifyItemChanged(position)
            clickListener(items[position].date)
        }
        if (currentDate == items[position].date && currentDateSelect) {
            holder.bindSelected()
            currentDateSelect = false
            selectedDate = items[holder.absoluteAdapterPosition].date
            selectedItemIndex = holder.absoluteAdapterPosition
        }
    }

    override fun getItemCount(): Int = items.size
}
