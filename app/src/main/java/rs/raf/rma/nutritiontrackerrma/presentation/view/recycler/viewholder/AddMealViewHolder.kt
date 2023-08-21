package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.AddMealItemBinding
import java.text.SimpleDateFormat
import java.util.*

class AddMealViewHolder (private val itemBinding: AddMealItemBinding, private val context: Context) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: Meal, onButtonClick: (Meal, String, Date) -> Unit) {

        var spinnerSelected = ""

        val adapter = ArrayAdapter(context, R.layout.spinner_tv, context.resources.getStringArray(R.array.spinnerMeals))
        adapter.setDropDownViewResource(R.layout.spinner_tv)
        itemBinding.menuSpinner.adapter = adapter

        itemBinding.menuSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = context.resources.getStringArray(R.array.spinnerMeals)[position]
                spinnerSelected = selected
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerSelected = "nothingSelected"
            }

        }

        itemBinding.mealNameTextView.text = meal.strMeal

        var dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currDate = Date()
        val currDateString = dateFormat.format(currDate)
        itemBinding.selectDateBtn.text = currDateString
        var selectedDate = dateFormat.parse(itemBinding.selectDateBtn.text.toString())

        itemBinding.addBtn.setOnClickListener{
            selectedDate = dateFormat.parse(itemBinding.selectDateBtn.text.toString())
            onButtonClick(meal, spinnerSelected, selectedDate)
        }

        itemBinding.cancelBtn.setOnClickListener{
            onButtonClick(meal, "cancel", selectedDate)
        }


        itemBinding.root.setOnClickListener{
        }

        itemBinding.selectDateBtn.setOnClickListener{
            showDatePickerDialog(itemBinding, context)
            selectedDate = dateFormat.parse(itemBinding.selectDateBtn.text.toString())
        }

        Glide
            .with(context)
            .load(meal.strMealThumb)
            .into(itemBinding.mealImageView)
    }

    private fun showDatePickerDialog(itemBinding: AddMealItemBinding, context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                itemBinding.selectDateBtn.text = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

}