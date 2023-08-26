package rs.raf.rma.nutritiontrackerrma.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentFilterBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentListMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.FragmentMealPageBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter.MealsAdapter
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import timber.log.Timber

class MealPageFragment : Fragment(R.layout.fragment_meal_page) {

    private val mealsViewModel: MealsContract.ViewModel by sharedViewModel<MealsViewModel>()

    private var _binding: FragmentMealPageBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initObservers()
    }

    private fun initObservers() {
        mealsViewModel.getSingleMeal("52772")
        mealsViewModel.mealsState2.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })


    }
    private fun initUi(meal : Meal) {

        binding.mealNameTextView.text = meal.strMeal
        binding.descriptionTextView.text = meal.strInstructions
        binding.videoLinkTextView.text = meal.strYoutube
        binding.areaTextView.text = meal.strArea


        val ingredients = ArrayList<String>().apply {
            meal.strIngredient1?.let { add(it) }
            meal.strIngredient2?.let { add(it) }
            meal.strIngredient3?.let { add(it) }
            meal.strIngredient4?.let { add(it) }
            meal.strIngredient5?.let { add(it) }
            meal.strIngredient6?.let { add(it) }
            meal.strIngredient7?.let { add(it) }
            meal.strIngredient8?.let { add(it) }
            meal.strIngredient9?.let { add(it) }
            meal.strIngredient10?.let { add(it) }
            meal.strIngredient11?.let { add(it) }
            meal.strIngredient12?.let { add(it) }
            meal.strIngredient13?.let { add(it) }
            meal.strIngredient14?.let { add(it) }
            meal.strIngredient15?.let { add(it) }
            meal.strIngredient16?.let { add(it) }
            meal.strIngredient17?.let { add(it) }
            meal.strIngredient18?.let { add(it) }
            meal.strIngredient19?.let { add(it) }
            meal.strIngredient20?.let { add(it) }
        }

        val measures = ArrayList<String>().apply {
            meal.strMeasure1?.let { add(it) }
            meal.strMeasure2?.let { add(it) }
            meal.strMeasure3?.let { add(it) }
            meal.strMeasure4?.let { add(it) }
            meal.strMeasure5?.let { add(it) }
            meal.strMeasure6?.let { add(it) }
            meal.strMeasure7?.let { add(it) }
            meal.strMeasure8?.let { add(it) }
            meal.strMeasure9?.let { add(it) }
            meal.strMeasure10?.let { add(it) }
            meal.strMeasure11?.let { add(it) }
            meal.strMeasure12?.let { add(it) }
            meal.strMeasure13?.let { add(it) }
            meal.strMeasure14?.let { add(it) }
            meal.strMeasure15?.let { add(it) }
            meal.strMeasure16?.let { add(it) }
            meal.strMeasure17?.let { add(it) }
            meal.strMeasure18?.let { add(it) }
            meal.strMeasure19?.let { add(it) }
            meal.strMeasure20?.let { add(it) }
        }


        for (s : String in ingredients){
            if(s != "")
                binding.ingredientsTextView.text = binding.ingredientsTextView.text.toString() + ", " + s
        }
        for (s : String in ingredients){
            if(s != "")
                binding.ingredientsTextView.text = binding.ingredientsTextView.text.toString() + ", " + s
        }


        Glide
            .with(this)
            .load(meal.strMealThumb)
            .into(binding.mealImageView)
    }

    private fun renderState(state: MealPageState) {
        when (state) {
            is MealPageState.Success -> {
                showLoadingState(false)
                initUi(state.mealss[0])
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
        binding.mealImageView.isVisible = !loading
        binding.mealNameTextView.isVisible = !loading
        binding.descriptionTextView.isVisible = !loading
        binding.ingredientsTextView.isVisible = !loading
        binding.videoLinkTextView.isVisible = !loading

        binding.loadingPb.isVisible = loading
    }


}