package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.databinding.UpdateMealItemBinding
import java.text.SimpleDateFormat
import java.util.*

class UpdateMealViewHolder (private val itemBinding: UpdateMealItemBinding, private val context: Context) : RecyclerView.ViewHolder(itemBinding.root) {

    private val REQUEST_IMAGE_CAPTURE = 1

    fun bind(meal: SavedMeal, onButtonClick: (SavedMeal, String, Date) -> Unit) {

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
        val dateString = dateFormat.format(meal.date)
        itemBinding.selectDateBtn.text = dateString
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

        itemBinding.mealImageView.setOnClickListener {
 /*           val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(context.packageManager) != null) {
                startActivityForResult(
                    context as Activity,
                    takePictureIntent,
                    REQUEST_IMAGE_CAPTURE,
                    null
                )
            }
*/
            showDialogue("CAMERA")

        }

        Glide
            .with(context)
            .load(meal.strMealThumb)
            .into(itemBinding.mealImageView)
    }

    private fun showDatePickerDialog(itemBinding: UpdateMealItemBinding, context: Context) {
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

    private fun showDialogue(text: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val dialogEditText: TextView = dialogView.findViewById(R.id.dialogEditText)

        // Set the title and text in the dialog
        dialogEditText.text = text
        dialogEditText.isFocusable = false
        dialogEditText.isClickable = false
        dialogEditText.isLongClickable = false

        val dialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                // Handle the OK button click if needed
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }
}