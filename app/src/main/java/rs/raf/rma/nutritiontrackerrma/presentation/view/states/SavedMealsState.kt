package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

sealed class SavedMealsState {
    object Loading: SavedMealsState()
    object DataFetched: SavedMealsState()
    data class Success(val meals: List<SavedMeal>): SavedMealsState()
    data class Error(val message: String): SavedMealsState()
}