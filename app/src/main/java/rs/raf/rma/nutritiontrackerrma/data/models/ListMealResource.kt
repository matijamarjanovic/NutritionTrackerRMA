package rs.raf.rma.nutritiontrackerrma.data.models

sealed class ListMealResource<out T> {
    data class Success<out T>(val data: T) : ListMealResource<T>()
    data class Loading<out T>(val message: String = "") : ListMealResource<T>()
    data class Error<out T>(val error: Throwable = Throwable(), val data: T? = null): ListMealResource<T>()
}