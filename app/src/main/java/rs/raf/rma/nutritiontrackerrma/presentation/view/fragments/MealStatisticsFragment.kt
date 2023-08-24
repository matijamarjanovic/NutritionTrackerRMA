package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import SharedPreferencesManager
import com.github.mikephil.charting.charts.BarChart
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentMealStatisticsBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealStatisticsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.GraphState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealStatisticsViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MealStatisticsFragment() : Fragment(R.layout.fragment_meal_statistics) {

    private lateinit var barChart: BarChart
    private val statisticViewModel: MealStatisticsContract.ViewModel by sharedViewModel<MealStatisticsViewModel>()

    private var _binding: FragmentMealStatisticsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    private fun init() {
        initUi()
        initObservers()
    }
    private fun initUi() {
        initGraph()
        initListeners()
    }

    private fun initObservers() {
        statisticViewModel.graphState.observe(viewLifecycleOwner,{
            Timber.e(it.toString())
            renderState(it)
        })

    }
    var isButtonClicked = false
    private fun initListeners() {
        binding.myButton.setOnClickListener {
            barChart = binding.barChart // Assuming you have a BarChart view in your XML layout

            // Customize the appearance of the chart (optional)
            barChart.setDrawBarShadow(false)
            barChart.setDrawValueAboveBar(true)

            barChart.setPinchZoom(false)
            barChart.isDragEnabled = false
            barChart.setDoubleTapToZoomEnabled(false)
            // Configure the X-axis (optional)
            val xAxis = barChart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            val dayNames = generateDayNamesList()
            xAxis.valueFormatter = IndexAxisValueFormatter(dayNames)
            val generateDate =generateDateList()

            if (isButtonClicked) {
                val sharedPreferencesManager=SharedPreferencesManager.getInstance()
                val username=sharedPreferencesManager.getUsername()?:""
                val a= statisticViewModel
                    .getMealsIn7DaysByNumbers(username,generateDate)
                binding.myButton.text = "change to calories"
            } else {
                val sharedPreferencesManager=SharedPreferencesManager.getInstance()
                val username=sharedPreferencesManager.getUsername()?:""
                val a= statisticViewModel
                    .getMealsIn7DaysByCalories(username,generateDate)
                binding.myButton.text = "change to days"
            }

            // Toggle the flag
            isButtonClicked = !isButtonClicked
        }
    }
    private fun initGraph() {
        barChart = binding.barChart // Assuming you have a BarChart view in your XML layout

        // Customize the appearance of the chart (optional)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)

        barChart.setPinchZoom(false)
        barChart.isDragEnabled = false
        barChart.setDoubleTapToZoomEnabled(false)

        // Configure the X-axis (optional)
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        val dayNames = generateDayNamesList()
        xAxis.valueFormatter = IndexAxisValueFormatter(dayNames)
        //println("AS"+day)

        val generateDate =generateDateList()
        val sharedPreferencesManager=SharedPreferencesManager.getInstance()
        val username=sharedPreferencesManager.getUsername()?:""
        println("11 "+username)
        val a= statisticViewModel
            .getMealsIn7DaysByNumbers(username,generateDate)

    }
    private fun renderState(state: GraphState ) {
        when (state){
            is GraphState.Success -> {
                showLoadingState(false)
                val data = state.days
                val entries = data.mapIndexed { index, value ->
                    BarEntry(index.toFloat(), value.toFloat())
                }
                val dataSet = BarDataSet(entries, "Label") // You can customize the label
                val barData = BarData(dataSet)
                barChart.data = barData
                barChart.invalidate()
            }
            is GraphState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is GraphState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is GraphState.Loading -> {
                showLoadingState(true)
            }
        }
    }
    private fun showLoadingState(loading: Boolean) {

    }
    fun getDateFormatedByDaysBack(n: Int): String {
        val calendar = Calendar.getInstance()
        calendar.time = Date()

        // Subtract 'n' days from the current date
        calendar.add(Calendar.DAY_OF_YEAR, -n)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
    fun generateDateList(): List<String> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        val dateList = ArrayList<String>()

        // Add the current day
        dateList.add(dateFormat.format(calendar.time))

        // Add the previous 6 days
        for (i in 1 until 7) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            dateList.add(dateFormat.format(calendar.time))
        }

        // Reverse the list so that it starts with the oldest date
        dateList.reverse()

        return dateList
    }
    fun generateDayNamesList(): List<String> {
        val dayNames = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        val calendar = Calendar.getInstance()

        val dayNameList = ArrayList<String>()

        // Add the current day's name
        dayNameList.add(dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1])

        // Add the previous 6 days' names
        for (i in 1 until 7) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            dayNameList.add(dayNames[calendar.get(Calendar.DAY_OF_WEEK) - 1])
        }

        // Reverse the list so that it starts with the oldest day name
        dayNameList.reverse()

        return dayNameList
    }
}