package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import SharedPreferencesManager
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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentListMealBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.*
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel

import timber.log.Timber

class ListMealFragment : Fragment(R.layout.fragment_list_meal) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mealsViewModel: MealsContract.ViewModel by sharedViewModel<MealsViewModel>()

    private var _binding: FragmentListMealBinding ? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val sharedPreferencesManager = SharedPreferencesManager.getInstance()
    val username = sharedPreferencesManager.getUsername()?:""

    private lateinit var adapter: SavedMealsAdapter
    private lateinit var adapter2: SingleSavedMealAdapter
    private lateinit var adapter3: UpdateMealAdapter

    private lateinit var meal : SavedMeal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMealBinding.inflate(inflater, container, false)
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
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {

        binding.backButton.visibility = View.GONE

        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = SavedMealsAdapter{text ->

            mealsViewModel.getSingleSavedMeal(text)

            binding.searchBar.visibility = View.GONE
            binding.backButton.visibility = View.VISIBLE

            binding.listRv.adapter = adapter2
        }
        adapter2 = SingleSavedMealAdapter { text ->


             if(text == "edit"){
               binding.listRv.adapter = adapter3
            }else if(text == "delete"){
                mealsViewModel.deleteMeal(meal.idMeal.toString())
                showDialogue("Meal successfully deleted from the database.")
                binding.listRv.adapter = adapter
                binding.backButton.visibility = View.GONE

                 mealsViewModel.getAllSavedMeals(username)
            }else
                showDialogue(text)

        }
        adapter3 = UpdateMealAdapter { meal, text, date ->

            if (text == "cancel") {
                binding.listRv.adapter = adapter2
            } else if (text.contains("nothingSelected")) {
                showDialogue("Nothing is selected in the drop down menu.")
            }else if(text=="ph"){

            }else{
                mealsViewModel.updateMeal(meal, text, date)
                showDialogue("Meal successfully updated.")
                binding.listRv.adapter = adapter
                binding.backButton.visibility = View.GONE
                binding.searchBar.visibility = View.VISIBLE

                mealsViewModel.getAllSavedMeals(username)
            }

        }

        binding.listRv.adapter = adapter

    }

    private fun initListeners() {
        binding.searchBar.doAfterTextChanged {
            val filter = it.toString()
            mealsViewModel.getSavedMealByName(filter)
        }

        binding.backButton.setOnClickListener{
            if (binding.listRv.adapter == adapter2){
                binding.listRv.adapter = adapter
                binding.backButton.visibility = View.GONE
                binding.searchBar.visibility = View.VISIBLE

                mealsViewModel.getAllSavedMeals(username)

            }else if(binding.listRv.adapter == adapter3){
                binding.listRv.adapter = adapter2
            }
        }
        binding.searchBar.isEnabled = false
    }

    private fun initObservers() {

        mealsViewModel.savedMealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        mealsViewModel.savedMealState2.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState2(it)
        })


        mealsViewModel.getAllSavedMeals(username)
    }

    private fun renderState(state: SavedMealsState) {
        when (state) {
            is SavedMealsState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is SavedMealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealsState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderState2(state: SavedMealPageState) {

        when (state) {
            is SavedMealPageState.Success -> {
                showLoadingState(false)
                meal = state.meals
                adapter2.submitList(state.mealss)
                adapter3.submitList(state.mealss)

            }
            is SavedMealPageState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealPageState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealPageState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.searchBar.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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