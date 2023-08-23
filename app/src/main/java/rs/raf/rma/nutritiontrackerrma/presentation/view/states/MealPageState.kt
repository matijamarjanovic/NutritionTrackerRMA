package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

sealed class MealPageState {

    object Loading: MealPageState()
    object DataFetched: MealPageState()
    data class Success(val mealss: List<Meal>): MealPageState()
    data class Error(val message: String): MealPageState()
}
