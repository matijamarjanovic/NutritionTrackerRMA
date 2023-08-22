package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.IngredientsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState

interface IngredientsContract {

    interface ViewModel {

        val ingredientsState: LiveData<IngredientsState>
        fun fetchAllIngredients()
        fun getAllIngredients()
        fun getAllIngredientsAscending()
        fun getAllIngredientsDescending()
        fun getItemByName(name: String)
    }

}