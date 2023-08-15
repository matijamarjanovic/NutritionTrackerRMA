package rs.raf.rma.nutritiontrackerrma.presentation.view.states

sealed class AddListMealState {
    object Success: AddListMealState()
    data class Error(val message: String): AddListMealState()
}