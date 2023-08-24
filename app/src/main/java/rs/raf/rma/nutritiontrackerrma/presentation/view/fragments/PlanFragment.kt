package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentListMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentPlanBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PlanFragment : Fragment(R.layout.fragment_plan) {

    private val mealsViewModel: MealsContract.ViewModel by sharedViewModel<MealsViewModel>()

    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!

    var mealList : ArrayList<String> = ArrayList()
    var mealListFinal : ArrayList<String> = ArrayList(Collections.nCopies(21, ""))

    var savedMeals : ArrayList<SavedMeal> = ArrayList()
    var meals : ArrayList<ListMeal> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }



    private fun init() {
        initObservers()
        initListeners()
        initSpinners()
    }

    lateinit var adapter : ArrayAdapter<String>
    private fun initSpinners() {


        if (adapter != null) {
            initSpinner(binding.monSpinnerB, 0, adapter!!)
            initSpinner(binding.monSpinnerL, 1, adapter!!)
            initSpinner(binding.monSpinnerD, 2, adapter!!)
            initSpinner(binding.tueSpinnerB, 3, adapter!!)
            initSpinner(binding.tueSpinnerL, 4, adapter!!)
            initSpinner(binding.tueSpinnerD, 5, adapter!!)
            initSpinner(binding.wedSpinnerB, 6, adapter!!)
            initSpinner(binding.wedSpinnerL, 7, adapter!!)
            initSpinner(binding.wedSpinnerD, 8, adapter!!)
            initSpinner(binding.thurSpinnerB, 9, adapter!!)
            initSpinner(binding.thurSpinnerL, 10, adapter!!)
            initSpinner(binding.thurSpinnerD, 11, adapter!!)
            initSpinner(binding.friSpinnerB, 12, adapter!!)
            initSpinner(binding.friSpinnerL, 13, adapter!!)
            initSpinner(binding.friSpinnerD, 14, adapter!!)
            initSpinner(binding.satSpinnerB, 15, adapter!!)
            initSpinner(binding.satSpinnerL, 16, adapter!!)
            initSpinner(binding.satSpinnerD, 17, adapter!!)
            initSpinner(binding.sunSpinnerB, 18, adapter!!)
            initSpinner(binding.sunSpinnerL, 19, adapter!!)
            initSpinner(binding.satSpinnerD, 20, adapter!!)
        }


    }

    private fun initSpinner(spinner: Spinner, mealNum : Int, adapter : ArrayAdapter<String>) {

        adapter?.setDropDownViewResource(R.layout.spinner_tv)

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = mealList[position]
                if (selected != null) {
                    mealListFinal[mealNum] = selected
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                mealListFinal[mealNum] = ""

            }
        }
    }
    private fun setAdapters(adapter: ArrayAdapter<String>){
        binding.monSpinnerB.adapter = adapter
        binding.monSpinnerL.adapter = adapter
        binding.monSpinnerD.adapter = adapter
        binding.tueSpinnerB.adapter = adapter
        binding.tueSpinnerL.adapter = adapter
        binding.tueSpinnerD.adapter = adapter
        binding.wedSpinnerB.adapter = adapter
        binding.wedSpinnerL.adapter = adapter
        binding.wedSpinnerD.adapter = adapter
        binding.thurSpinnerB.adapter = adapter
        binding.thurSpinnerL.adapter = adapter
        binding.thurSpinnerD.adapter = adapter
        binding.friSpinnerB.adapter = adapter
        binding.friSpinnerL.adapter = adapter
        binding.friSpinnerD.adapter = adapter
        binding.satSpinnerB.adapter = adapter
        binding.satSpinnerL.adapter = adapter
        binding.satSpinnerD.adapter = adapter
        binding.sunSpinnerB.adapter = adapter
        binding.sunSpinnerL.adapter = adapter
        binding.sunSpinnerD.adapter = adapter

    }

    private fun initListeners() {


        binding.sourceRg.setOnCheckedChangeListener { group, checkedId ->


            when(checkedId){
                R.id.radioApi -> {
                    mealsViewModel.getAllMeals()

                }
                R.id.radioDb -> {

                    val sharedPreferencesManager=SharedPreferencesManager.getInstance()
                    val username=sharedPreferencesManager.getUsername()?:""
                    mealsViewModel.getAllSavedMeals(username)


                }
            }
        }
    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)

                mealList.clear()
                meals = ArrayList(state.meals)
                for (m in state.meals){
                    mealList.add(m.strMeal)
                }
                adapter = context?.let { ArrayAdapter(it, R.layout.spinner_tv, mealList) }!!
                adapter?.let { setAdapters(it) }

            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderStateSaved(state: SavedMealsState) {
        when (state) {
            is SavedMealsState.Success -> {
                showLoadingState(false)
                savedMeals = ArrayList(state.meals)

                mealList.clear()
                for (m in state.meals){
                    m.strMeal?.let { mealList.add(it) }
                }
                adapter = context?.let { ArrayAdapter(it, R.layout.spinner_tv, mealList) }!!
                adapter?.let { setAdapters(it) }
            }
            is SavedMealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealsState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun initObservers() {
        mealsViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        mealsViewModel.savedMealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateSaved(it)
        })

        binding.radioApi.isChecked = true
        binding.emailEt.clearFocus()
        binding.scrollView.smoothScrollTo(0,0)

        mealsViewModel.getAllMeals()

        adapter = context?.let { ArrayAdapter(it, R.layout.spinner_tv, mealList) }!!
        adapter?.let { setAdapters(it) }


    }
    private fun showLoadingState(loading: Boolean) {
        binding.monLay.isVisible = !loading
        binding.tueLay.isVisible = !loading
        binding.wedLay.isVisible = !loading
        binding.thurLay.isVisible = !loading
        binding.friLay.isVisible = !loading
        binding.satLay.isVisible = !loading
        binding.sunLay.isVisible = !loading

        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogue(text: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val dialogEditText: TextView = dialogView.findViewById(R.id.dialogEditText)

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