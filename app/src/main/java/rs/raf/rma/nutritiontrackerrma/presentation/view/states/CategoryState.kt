package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import androidx.constraintlayout.utils.widget.MockView
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.CategoryData


sealed class CategoryState {
    object Loading : CategoryState()
    object DataFetched : CategoryState()
    data class Success(val categories : List<Category>) : CategoryState()
    data class Error(val message : String): CategoryState()
}