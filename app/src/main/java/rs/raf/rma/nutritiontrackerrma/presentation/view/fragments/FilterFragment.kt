package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
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
import rs.raf.rma.nutritiontrackerrma.data.models.ingredients.Ingredient
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentFilterBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentHomepageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.FilterContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.IngredientsContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.*
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.*
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.FilterViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.IngredientsViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.SearchType
import timber.log.Timber

class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val filterViewModel : FilterContract.ViewModel by sharedViewModel<FilterViewModel>()
    private val mealsViewModel : MealsContract.ViewModel by sharedViewModel<MealsViewModel> ()
    //private val ingredientsViewModel : IngredientsContract.ViewModel by sharedViewModel<IngredientsViewModel>()

    private var _binding : FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : FilterAdapter
    private lateinit var adapter1 : FilterAdapter
    private lateinit var adapter2 : MealsAdapter
    private lateinit var adapter3: SingleMealAdapter
    private lateinit var adapter4: AddMealAdapter

    private var meal : Meal? = null
    private var mealList : ArrayList<ListMeal> = ArrayList()

    var allIngredients : ArrayList<Ingredient> = ArrayList()

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
        binding.tagsRb.visibility = View.GONE
        mealsViewModel.setSelectedSearchType(SearchType.BY_NAME)


    }

    private fun init() {
        initUi()
        initObservers()
    }
    private fun initUi() {
        initRecycler()
        initListeners()
    }
    var filterTest=""
    private fun initRecycler() {

        // binding.catRb.isChecked=true
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = FilterAdapter{ text ->
            filterTest=text
            binding.listRv.adapter = adapter2
            binding.backButton.visibility = View.VISIBLE


            mealsViewModel.getAllMeals()
            mealsViewModel.fetchAllMealsByArea(text)

            if(binding.areaRb.isChecked)
                mealsViewModel.fetchAllMealsByArea(text)
            else if (binding.catRb.isChecked)
                mealsViewModel.fetchAllMealsByCategory(text)
            else if (binding.ingRb.isChecked)
                mealsViewModel.fetchAllMealsByIngridient(text)




            binding.linearLay.visibility = View.GONE
            binding.kcalFilterLay.visibility = View.VISIBLE
            binding.tagsRb.visibility = View.VISIBLE
            binding.tagsRb.isChecked = true

            binding.sortDescendingBtn.visibility = View.GONE
            binding.sortAscendingBtn.visibility = View.GONE

        }

        adapter2 = MealsAdapter{text ->

            mealsViewModel.getSingleMeal(text)

            binding.searchBar.isEnabled = false
            binding.backButton.visibility = View.VISIBLE
            binding.kcalFilterLay.visibility = View.GONE

            binding.tagsRb.visibility=View.GONE

            binding.listRv.adapter = adapter3

            meal?.let {
                it.strArea?.let { it1 -> mealsViewModel.fetchAllMealsByArea(it1) }
            }
            binding.linearLay.visibility = View.GONE

        }

        adapter3 = SingleMealAdapter { text ->
            if(text == "add"){
                binding.listRv.adapter = adapter4
            }else
                showDialogue(text)

        }

        adapter4 = AddMealAdapter { meal, text, date ->
//            binding.tagsRb.visibility = View.GONE
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
    var tagFlag=1;
    private fun initListeners() {

        mealsViewModel.setSelectedSearchType(SearchType.BY_NAME)
        binding.searchBar.doAfterTextChanged { it1 ->
            val filter = it1.toString()
            filterViewModel.getItemByName(filter)
        }
        binding.tagsRb.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mealsViewModel.setSelectedSearchType(SearchType.BY_TAGS)
                if (binding.listRv.adapter == adapter2){
                    binding.searchBar.doAfterTextChanged {
                        val filter = it.toString()
                        mealsViewModel.getMealByTags(filter)
                    }
                }
            }else{
                if (binding.listRv.adapter == adapter2){
                    mealsViewModel.setSelectedSearchType(SearchType.BY_NAME)
                    binding.searchBar.doAfterTextChanged {
                        val filter = it.toString()
                        mealsViewModel.getMealByName(filter)
                    }
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

                binding.sortDescendingBtn.visibility = View.VISIBLE
                binding.sortAscendingBtn.visibility = View.GONE

                binding.linearLay.visibility = View.VISIBLE
                binding.kcalFilterLay.visibility = View.GONE
                binding.tagsRb.visibility = View.GONE



            }else if(binding.listRv.adapter == adapter3){
               // binding.tagsRb.visibility = View.GONE


                binding.listRv.adapter = adapter2
                binding.searchBar.isEnabled = true
                binding.kcalFilterLay.visibility = View.VISIBLE
                binding.tagsRb.visibility=View.VISIBLE


                binding.linearLay.visibility = View.GONE

                binding.sortDescendingBtn.visibility = View.GONE
                binding.sortAscendingBtn.visibility = View.GONE

            } else if(binding.listRv.adapter == adapter4){
           //     binding.tagsRb.visibility = View.GONE

                binding.listRv.adapter = adapter3

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

        binding.filterKcalBtn.setOnClickListener{
            val min = binding.fromEt.text.toString().toDouble()
            val max = binding.toEt.text.toString().toDouble()

            mealsViewModel.getAllMealsSortedByCal(min, max)
        }

        binding.resetBtn.setOnClickListener{
            binding.fromEt.setText("0")
            binding.toEt.setText("10000")
            binding.filterKcalBtn.performClick()
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


        mealsViewModel.caloriesState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState4(it)
        })

        mealsViewModel.mealsState3.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderStateGetAllSingleMeals(it)
        })


        binding.areaRb.isChecked = true
        binding.searchBar.clearFocus()

        binding.kcalFilterLay.visibility = View.GONE
        binding.tagsRb.visibility =View.GONE
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

    var from = 0.0
    var to = 10000.0

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
                meal = state.mealss[0]

                mealsViewModel.addKcalToMeal(state.mealss as ArrayList<Meal>)
/*
                adapter3.submitList(state.mealss)
                adapter4.submitList(state.mealss)
*/
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


    private fun renderState4(state: CaloriesState) {

        when (state) {
            is CaloriesState.Success -> {
                showLoadingState(false)

                adapter3.submitList(state.meal)
                adapter4.submitList(state.meal)
            }
            is CaloriesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CaloriesState.DataFetched -> {
                showLoadingState(false)
                //Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CaloriesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun renderStateGetAllSingleMeals(state: MealPageState) {

        when (state) {
            is MealPageState.Success -> {
                showLoadingState(false)

                val list = state.mealss.map { it.strMealThumb?.let { it1 ->
                        ListMeal(
    it.idMeal, it.strMeal,it.strCategory,it.strArea,it.strInstructions,it1,it.strTags,it.strYoutube,it.calories,
    it.strIngredient1,it.strIngredient2,it.strIngredient3,it.strIngredient4,it.strIngredient5,it.strIngredient6,it.strIngredient7,it.strIngredient8,
    it.strIngredient9,it.strIngredient10,it.strIngredient11,it.strIngredient12,it.strIngredient13,it.strIngredient14,it.strIngredient15,it.strIngredient16,
    it.strIngredient17,it.strIngredient18,it.strIngredient19,it.strIngredient20,

    it.strMeasure1,it.strMeasure2,it.strMeasure3,it.strMeasure4,it.strMeasure5,it.strMeasure6,it.strMeasure7,it.strMeasure8,it.strMeasure9,it.strMeasure10,
    it.strMeasure11,it.strMeasure12,it.strMeasure13,it.strMeasure14,it.strMeasure15,it.strMeasure16,it.strMeasure17, it.strMeasure18,it.strMeasure19,it.strMeasure20,
                            it.caloriesList
    )
                } }
                adapter2.submitList(list)

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