package rs.raf.rma.nutritiontrackerrma.presentation.view.states

import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter


sealed class FilterState {
    object Loading : FilterState()
    object DataFetched : FilterState()
    data class Success(val filtredItems : List<Filter>) : FilterState()
    data class Error(val message : String): FilterState()
}