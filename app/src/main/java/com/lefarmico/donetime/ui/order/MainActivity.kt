package com.lefarmico.donetime.ui.order
import android.widget.Toast
import com.lefarmico.donetime.Exercise
import com.lefarmico.donetime.ExerciseDate
import com.lefarmico.donetime.ItemAdapter
import com.lefarmico.donetime.ItemType
import com.lefarmico.donetime.ItemViewType
import com.lefarmico.donetime.databinding.ActivityMainBinding
import com.lefarmico.donetime.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, OrderViewModel>(
    { ActivityMainBinding.inflate(it) },
    OrderViewModel::class.java
) {
    override fun setUpViews() {
        binding.listRecycler.adapter = ItemAdapter().apply {
            val newItems: MutableList<ItemType> = mutableListOf(
                ExerciseDate("04.07.2021"),
                Exercise("Жим лежа", "Это когда жмешь лежа.")
            )
            items = newItems
            setOnClickEvent {
                when (it.type) {
                    ItemViewType.EXERCISE -> {
                        Toast.makeText(this@MainActivity, "Ex", Toast.LENGTH_SHORT).show()
                    }
                    ItemViewType.DATE -> {
                        Toast.makeText(this@MainActivity, "Date", Toast.LENGTH_SHORT).show()
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
