package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category


sealed class CategoryState {
    object Loading : CategoryState()
    object DataFetched : CategoryState()
    data class Success(val categories : List<Category>) : CategoryState()
    data class Error(val message : String): CategoryState()
}