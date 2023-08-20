package rs.raf.rma.nutritiontrackerrma.presentation.view.states
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

sealed class MealsState {
    object Loading: MealsState()
    object DataFetched: MealsState()
    data class Success(val meals: List<ListMeal>): MealsState()
    data class Error(val message: String): MealsState()
}