package com.lefarmico.donetime.ui.order

import android.widget.Toast
import com.lefarmico.donetime.EnumViewTypes
import com.lefarmico.donetime.Exercise
import com.lefarmico.donetime.ExerciseDate
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.ui.base.BaseActivity
import com.lefarmico.lerecycle.ItemType
import com.lefarmico.lerecycle.LeRecyclerAdapter
import com.lefarmico.lerecycle.extractValues

class MainActivity : BaseActivity<ActivityMainBinding, OrderViewModel>(
    { ActivityMainBinding.inflate(it) },
    OrderViewModel::class.java
) {
    override fun setUpViews() {
        binding.listRecycler.adapter = LeRecyclerAdapter().apply {
            setItemTypes(
                extractValues<EnumViewTypes>()
            )
            val newItems: MutableList<ItemType> = mutableListOf(
                ExerciseDate("04.07.2021"),
                Exercise("Жим лежа", "Это когда жмешь лежа."),
                Exercise("Жим лежа", "Это когда жмешь лежа."),
                Exercise("Жим лежа", "Это когда жмешь лежа.")
            )
            items = newItems
            setOnClickEvent {
                when (it.type) {
                    EnumViewTypes.EXERCISE -> {
                        val exercise = it as Exercise
                        Toast.makeText(this@MainActivity, "Ex ${exercise.name}", Toast.LENGTH_SHORT).show()
                    }
                    EnumViewTypes.DATE -> {
                        val date = it as ExerciseDate
                        Toast.makeText(this@MainActivity, "Ex ${date.date}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun observeView() {
        super.observeView()
    }

    override fun observeData() {
        super.observeData()
    }
}
