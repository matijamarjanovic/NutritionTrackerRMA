package rs.raf.rma.nutritiontrackerrma.presentation.view.states.user
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.models.user.User

sealed class UserState {
    object Loading: UserState()
    object DataFetched: UserState()
    data class Success(val users: List<User>): UserState()
    data class Error(val message: String): UserState()
}