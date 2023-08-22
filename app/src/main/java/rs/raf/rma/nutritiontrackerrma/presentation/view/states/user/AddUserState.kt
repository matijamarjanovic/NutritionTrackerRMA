package rs.raf.rma.nutritiontrackerrma.presentation.view.states.user

sealed class AddUserState {
    object Success: AddUserState()
    data class Error(val message: String): AddUserState()
}