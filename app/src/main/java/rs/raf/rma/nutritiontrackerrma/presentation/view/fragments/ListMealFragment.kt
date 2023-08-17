package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

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
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentListMealBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.MealsAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.SingleMealAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel

import timber.log.Timber

class ListMealFragment : Fragment(R.layout.fragment_list_meal) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mealsViewModel: MealsContract.ViewModel by sharedViewModel<MealsViewModel>()

    private var _binding: FragmentListMealBinding ? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealsAdapter
    private lateinit var adapter2: SingleMealAdapter

    private lateinit var meal : Meal

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
        adapter = MealsAdapter{text ->

            mealsViewModel.getSingleMeal(text)
            mealsViewModel.fetchAllMealsByArea(meal.strArea)

            binding.searchBar.visibility = View.GONE
            binding.backButton.visibility = View.VISIBLE

            binding.listRv.adapter = adapter2

        }
        adapter2 = SingleMealAdapter { text ->
            showDialogue(text)
        }
        binding.listRv.adapter = adapter

    }

    private fun initListeners() {
        binding.searchBar.doAfterTextChanged {
            val filter = it.toString()
            mealsViewModel.getMealByName(filter)
        }

        binding.backButton.setOnClickListener{

                binding.listRv.adapter = adapter
                binding.backButton.visibility = View.GONE

        }
    }

    private fun initObservers() {


        mealsViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        mealsViewModel.mealsState2.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState2(it)
        })

        mealsViewModel.getAllMeals()
        mealsViewModel.fetchAllMealsByArea("British")
    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderState2(state: MealPageState) {

        when (state) {
            is MealPageState.Success -> {
                showLoadingState(false)
                meal = state.meals
                adapter2.submitList(state.mealss)
            }
            is MealPageState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealPageState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealPageState.Loading -> {
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
        val dialogEditText: EditText = dialogView.findViewById(R.id.dialogEditText)

        // Set the title and text in the dialog
        dialogEditText.setText(text)

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