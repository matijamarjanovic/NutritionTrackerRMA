package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

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
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentCalorieCalculatorBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentFilterBinding
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
import kotlin.math.roundToInt

class CalorieCalculatorFragment() : Fragment(R.layout.fragment_calorie_calculator) {

    private var _binding : FragmentCalorieCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalorieCalculatorBinding.inflate(inflater, container, false)
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
        initListeners()
    }

    private fun initObservers() {


    }
    private fun initListeners() {
        binding.button.setOnClickListener {
            var calorije = 0.0

            if (!binding.editTextText.text.toString().isEmpty() && !binding.editTextText2.text.toString().isEmpty() && !binding.editTextText3.text.toString().isEmpty()) {

                val text1 = binding.editTextText.text.toString()
                val text2 = binding.editTextText2.text.toString()
                val text3 = binding.editTextText3.text.toString()

                // Check if the texts contain only numbers using regex
                if (text1.matches(Regex("[0-9]+")) && text2.matches(Regex("[0-9]+")) && text3.matches(Regex("[0-9]+"))) {
                    // All three fields contain only numbers
                    // Your code for the desired action here

                var tezina = binding.editTextText.text.toString().toDouble()
                var visina = binding.editTextText2.text.toString().toDouble()
                var godine = binding.editTextText3.text.toString().toDouble()
                var gender = "a"

                if (binding.radioFemale.isChecked) {
                    gender = "female"
                } else if (binding.radioMale.isChecked) {
                    gender = "male"
                }else{
                    var string="obavezan je pol"
                }

                if (gender == "female") {
                    calorije = visina * 1.850 + tezina * 9.563 + godine * 4.676
                } else if (gender == "male") {
                    calorije = visina * 5.003 + tezina * 13.75 + godine * 6.755
                }
                var aktivnost = -1


                if (binding.radioOption1.isChecked) {
                    aktivnost = 0
                } else if (binding.radioOption2.isChecked) {
                    aktivnost = 1
                } else if (binding.radioOption3.isChecked) {
                    aktivnost = 2
                } else if (binding.radioOption4.isChecked) {
                    aktivnost = 3
                } else if (binding.radioOption5.isChecked) {
                    aktivnost = 4
                }

                if(aktivnost !=-1){
                    if (aktivnost == 0) {
                        calorije = calorije * 1.2
                    } else if (aktivnost == 1) {
                        calorije = calorije * 1.375
                    } else if (aktivnost == 2) {
                        calorije = calorije * 1.55
                    } else if (aktivnost == 3) {
                        calorije = calorije * 1.725
                    } else if (aktivnost == 4) {
                        calorije = calorije * 1.9
                    }
                }else{
                    var string="obavezna aktivnost"
                }
                if (calorije != 0.0 && aktivnost!=-1 && gender!="a") {
                    binding.textView5.setText(calorije.roundToInt().toString() + " KCal")
                }
                println("ad =" + calorije)
                } else {
                    // At least one of the fields contains non-numeric characters
                    // Handle the error or show a message to the user
                }

            }
            else{
                var string="sva polja su obavezna"
                println(string)
            }
        }
    }


}