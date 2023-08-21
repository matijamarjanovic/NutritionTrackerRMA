package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal

sealed class SavedMealPageState {

    object Loading: SavedMealPageState()
    object DataFetched: SavedMealPageState()
    data class Success(val meals: SavedMeal, val mealss: List<SavedMeal>): SavedMealPageState()
    data class Error(val message: String): SavedMealPageState()
}