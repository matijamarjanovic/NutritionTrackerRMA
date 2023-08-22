package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import rs.raf.rma.nutritiontrackerrma.data.models.ingredients.Ingredient


sealed class IngredientsState {
    object Loading : IngredientsState()
    object DataFetched : IngredientsState()
    data class Success(val ingredients : List<Ingredient>) : IngredientsState()
    data class Error(val message : String): IngredientsState()
}