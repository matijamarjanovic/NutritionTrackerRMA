package rs.raf.rma.nutritiontrackerrma.presentation.view.states
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

sealed class GraphState {
    object Loading: GraphState()
    object DataFetched: GraphState()
    data class Success(val days: List<Int>): GraphState()
    data class Error(val message: String): GraphState()
}