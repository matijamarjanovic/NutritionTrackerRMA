package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentHomepageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.AddMealAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.MealsAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.SingleMealAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class HomepageFragment() : Fragment(R.layout.fragment_homepage) {

    private val categoryViewModel : CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()
    private val mealsViewModel : MealsContract.ViewModel by sharedViewModel<MealsViewModel> ()

    private var _binding : FragmentHomepageBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : CategoryAdapter
    private lateinit var adapter2 : MealsAdapter
    private lateinit var adapter3: SingleMealAdapter
    private lateinit var adapter4: AddMealAdapter

    private var meal : Meal? = null

    private var curentPage=0
    private var itemsPerPage=8
    private var max=1;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
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
        binding.backButton.visibility = View.GONE

        adapter = CategoryAdapter { text ->

            if (text.length < 30) {
                binding.listRv.adapter = adapter2
                binding.backButton.visibility = View.VISIBLE
                binding.pageBackBtn.visibility = View.GONE
                binding.pageForwardBtn.visibility = View.GONE

                mealsViewModel.getAllMeals()
                mealsViewModel.fetchAllMealsByCategory(text)

            } else
                showDialogue(text)

        }

        adapter2 = MealsAdapter{text ->

            mealsViewModel.getSingleMeal(text)
            binding.pageBackBtn.visibility = View.GONE
            binding.pageForwardBtn.visibility = View.GONE

            binding.searchBar.isEnabled = false
            binding.backButton.visibility = View.VISIBLE

            binding.listRv.adapter = adapter3

            meal?.let {
                mealsViewModel.fetchAllMealsByArea(it.strArea)
            }


        }

        adapter3 = SingleMealAdapter { text ->
            binding.pageBackBtn.visibility = View.GONE
            binding.pageForwardBtn.visibility = View.GONE
            if(text == "add"){
                binding.listRv.adapter = adapter4
            }else
                showDialogue(text)
        }

        adapter4 = AddMealAdapter { meal, text, date ->
            binding.pageBackBtn.visibility = View.GONE
            binding.pageForwardBtn.visibility = View.GONE
            if (text == "cancel"){
                binding.listRv.adapter = adapter3
            }else if(text.contains("nothingSelected")){
                showDialogue("Nothing is selected in the drop down menu.")
            }else{
                mealsViewModel.addMeal(meal, text, date)
                showDialogue("Meal successfully saved.")
            }
        }
        binding.listRv.adapter = adapter

    }

    private fun initListeners() {
        
        binding.searchBar.doAfterTextChanged { it1 ->
                val filter = it1.toString()
                categoryViewModel.getCategoryByName(filter)
                
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
                binding.pageBackBtn.visibility = View.VISIBLE
                binding.pageForwardBtn.visibility = View.VISIBLE
                binding.searchBar.doAfterTextChanged {
                    val filter = it.toString()
                    categoryViewModel.getCategoryByName(filter)
                }

            }else if(binding.listRv.adapter == adapter3){
                binding.listRv.adapter = adapter2
                binding.searchBar.isEnabled = true
            }
            else if(binding.listRv.adapter == adapter4){
                binding.listRv.adapter = adapter3
            }

        }

        binding.pageBackBtn.setOnClickListener{
            if(curentPage!=0){
                curentPage -= 1
                 categoryViewModel.getAllCategoriesByPage(curentPage,itemsPerPage)
                 //categoryViewModel.fetchAllCategories()
            }
        }

        binding.pageForwardBtn.setOnClickListener{
            if(curentPage<max){
                curentPage += 1
                categoryViewModel.getAllCategoriesByPage(curentPage,itemsPerPage)
                //categoryViewModel.fetchAllCategories()
            }
        }

    }

    private fun initObservers() {
        categoryViewModel.categoryState.observe(viewLifecycleOwner, Observer {
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

        categoryViewModel.getAllCategoriesByPage(curentPage,itemsPerPage)
        categoryViewModel.fetchAllCategories()
        binding.searchBar.clearFocus()
    }

    private fun renderState(state: CategoryState) {
        when (state) {
            is CategoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.categories)
            }
            is CategoryState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CategoryState.DataFetched -> {
                showLoadingState(false)
//                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CategoryState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    var meals: ArrayList<ListMeal> =ArrayList()
    private fun renderState2(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                meals= ArrayList(state.meals)
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
                meal = state.mealss[0]
                adapter3.submitList(state.mealss)
                adapter4.submitList(state.mealss)
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