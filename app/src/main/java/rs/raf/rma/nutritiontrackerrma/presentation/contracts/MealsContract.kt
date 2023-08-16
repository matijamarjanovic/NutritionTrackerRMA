package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState

interface MealsContract {

    interface ViewModel {

        val mealsState: LiveData<MealsState>
        val addDone: LiveData<AddListMealState>

        fun fetchAllMealsByArea(area:String)
        fun fetchAllMealsByCategory(category: String)
        fun fetchAllMealsByIngridient(ingridient: String)

        fun getAllMeals()
        fun getMealByName(name: String)
        fun addMeal(listMeal: ListMeal)
    }

}