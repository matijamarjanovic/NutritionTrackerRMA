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
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentFilterBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentHomepageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.FilterContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.FilterAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.MealsAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.SingleMealAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.FilterViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import timber.log.Timber

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val filterViewModel : FilterContract.ViewModel by sharedViewModel<FilterViewModel>()
    private val mealsViewModel : MealsContract.ViewModel by sharedViewModel<MealsViewModel> ()

    private var _binding : FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : FilterAdapter
    private lateinit var adapter2 : MealsAdapter
    private lateinit var adapter3: SingleMealAdapter

    private var meal : Meal? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
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
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = FilterAdapter{ text ->

            binding.listRv.adapter = adapter2
            binding.backButton.visibility = View.VISIBLE

            mealsViewModel.getAllMeals()

            if(binding.areaRb.isChecked)
                mealsViewModel.fetchAllMealsByArea(text)
            else if (binding.catRb.isChecked)
                mealsViewModel.fetchAllMealsByCategory(text)
            else if (binding.ingRb.isChecked)
                mealsViewModel.fetchAllMealsByIngridient(text)

            binding.linearLay.visibility = View.GONE

        }

        adapter2 = MealsAdapter{text ->

            mealsViewModel.getSingleMeal(text)

            binding.searchBar.isEnabled = false
            binding.backButton.visibility = View.VISIBLE

            binding.listRv.adapter = adapter3

            meal?.let {
                mealsViewModel.fetchAllMealsByArea(it.strArea)
            }

        }

        adapter3 = SingleMealAdapter { text ->
            showDialogue(text)
        }
        binding.listRv.adapter = adapter
    }

    private fun initListeners() {
        binding.searchBar.doAfterTextChanged { it1 ->
            val filter = it1.toString()
            filterViewModel.getItemByName(filter)

            if (binding.listRv.adapter == adapter2){
                binding.searchBar.doAfterTextChanged {
                    val filter = it.toString()
                    mealsViewModel.getMealByName(filter)
                }
            }
        }

        binding.backButton.setOnClickListener{
            if (binding.listRv.adapter == adapter2){
                binding.listRv.adapter = adapter
                binding.backButton.visibility = View.GONE

                binding.searchBar.doAfterTextChanged {
                    val filter = it.toString()
                    filterViewModel.getItemByName(filter)
                }
                binding.linearLay.visibility = View.VISIBLE

            }else if(binding.listRv.adapter == adapter3){
                binding.listRv.adapter = adapter2
                binding.searchBar.isEnabled = true
            }

        }

        var area = true
        var cat = false
        var ing = false
        var asc = true

        binding.sortAscendingBtn.visibility = View.GONE

        binding.sortAscendingBtn.setOnClickListener{

            binding.sortDescendingBtn.visibility = View.VISIBLE
            binding.sortAscendingBtn.visibility = View.GONE
            asc = !asc

            if(area){
                filterViewModel.getAllAreasAscending()
                filterViewModel.fetchAllAreas()
            }else if(cat){
                filterViewModel.getAllCategoriesAscending()
                filterViewModel.fetchAllCategories()
            }else{
                filterViewModel.getAllIngredientsAscending()
                filterViewModel.fetchAllIngredients()
            }

        }

        binding.sortDescendingBtn.setOnClickListener{
            binding.sortDescendingBtn.visibility = View.GONE
            binding.sortAscendingBtn.visibility = View.VISIBLE
            asc = !asc

            if(area){
                filterViewModel.getAllAreasDescending()
                filterViewModel.fetchAllAreas()
            }else if(cat){
                filterViewModel.getAllCategoriesDescending()
                filterViewModel.fetchAllCategories()
            }else{
                filterViewModel.getAllIngredientsDescending()
                filterViewModel.fetchAllIngredients()
            }

        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if(asc){
                when (checkedId) {
                    R.id.areaRb -> {
                        filterViewModel.getAllAreasAscending()
                        filterViewModel.fetchAllAreas()

                        area = true
                        cat = false
                        ing = false
                    }
                    R.id.catRb -> {
                        filterViewModel.getAllCategoriesAscending()
                        filterViewModel.fetchAllCategories()

                        area = false
                        cat = true
                        ing = false
                    }
                    R.id.ingRb -> {
                        filterViewModel.getAllIngredientsAscending()
                        filterViewModel.fetchAllIngredients()

                        area = false
                        cat = false
                        ing = true
                    }
                }
            }else{
                when (checkedId) {
                    R.id.areaRb -> {
                        filterViewModel.getAllAreasDescending()
                        filterViewModel.fetchAllAreas()

                        area = true
                        cat = false
                        ing = false
                    }
                    R.id.catRb -> {
                        filterViewModel.getAllCategoriesDescending()
                        filterViewModel.fetchAllCategories()

                        area = false
                        cat = true
                        ing = false
                    }
                    R.id.ingRb -> {
                        filterViewModel.getAllIngredientsDescending()
                        filterViewModel.fetchAllIngredients()

                        area = false
                        cat = false
                        ing = true
                    }
                }
            }


        }
    }

    private fun initObservers() {
        filterViewModel.filterdItemsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })

        mealsViewModel.mealsState.observe(viewLifecycleOwner) {
            Timber.e(it.toString())
            renderState2(it)
        }

        mealsViewModel.mealsState2.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState3(it)
        })

        binding.areaRb.isChecked = true

    }

    private fun renderState(state: FilterState) {
        when (state) {
            is FilterState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.filtredItems)
            }
            is FilterState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is FilterState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is FilterState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderState2(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                adapter2.submitList(state.meals)
            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
//                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderState3(state: MealPageState) {

        when (state) {
            is MealPageState.Success -> {
                showLoadingState(false)
                meal = state.meals
                adapter3.submitList(state.mealss)
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