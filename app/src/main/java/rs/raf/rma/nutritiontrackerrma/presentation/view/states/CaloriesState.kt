package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

sealed class CaloriesState {
    object Loading: CaloriesState()
    object DataFetched: CaloriesState()
    data class Success(val meal: Meal): CaloriesState()
    data class Error(val message: String): CaloriesState()
}