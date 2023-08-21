package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import com.github.mikephil.charting.charts.BarChart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentHomepageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.MealsAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.SingleMealAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import timber.log.Timber

class MealStatisticsFragment() : Fragment(R.layout.fragment_meal_statistics) {

    private lateinit var barChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meal_statistics, container, false)
        barChart = view.findViewById(R.id.barChart)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Replace this with your code to fetch data from your local database
        val dataEntries = fetchDataFromLocalDatabase()

        // Create entries for the bar chart
        val entries = ArrayList<BarEntry>()
        for (i in dataEntries.indices) {
            entries.add(BarEntry(i.toFloat(), dataEntries[i].toFloat()))
        }

        // Create a dataset for the chart
        val dataSet = BarDataSet(entries, "Your Data Label")
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        // Create a data object and set it to the chart
        val barData = BarData(dataSet)
        barChart.data = barData

        // Customize the chart as needed
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.invalidate()
    }

    private fun fetchDataFromLocalDatabase(): List<Int> {
        // Replace this with your code to fetch data from your local database
        // Return a list of integers representing data for the past seven days
        // Example:
        return listOf(5, 10, 8, 15, 20, 12, 18)
    }

}