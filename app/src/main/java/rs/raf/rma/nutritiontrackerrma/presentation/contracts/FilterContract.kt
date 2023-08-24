package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState

interface FilterContract {

    interface ViewModel {

        val filterdItemsState: LiveData<FilterState>

        fun fetchAllCategories()
        fun fetchAllAreas()
        fun fetchAllIngredients()
        fun insertIngredientsIntoDatabase()
        fun getAllCategories()
        fun getAllAreas()
        fun getAllIngredients()
        fun getAllCategoriesAscending()
        fun getAllAreasAscending()
        fun getAllIngredientsAscending()
        fun getAllCategoriesDescending()
        fun getAllAreasDescending()
        fun getAllIngredientsDescending()


        //val filterState: LiveData<FilterState>
        //val addDone: LiveData<AddListMealState>

        fun getItemByName(name: String)
    }

}