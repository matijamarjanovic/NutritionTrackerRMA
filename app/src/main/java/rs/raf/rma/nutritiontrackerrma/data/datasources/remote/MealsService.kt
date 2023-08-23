package rs.raf.rma.nutritiontrackerrma.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMealResponse
import rs.raf.rma.nutritiontrackerrma.data.models.meals.singleMeals.SingleMealResponse

interface MealsService {
    @GET("filter.php")
    fun getAllMealsByArea(@Query("a") category: String): Observable<ListMealResponse>
    @GET("filter.php")
    fun getAllMealsByCategory(@Query("c") category: String): Observable<ListMealResponse>
    @GET("filter.php")
    fun getAllMealsByIngredient(@Query("i") ingredient: String): Observable<ListMealResponse>
    @GET("lookup.php?i=")
    fun singleMeal(@Query("i") id: String): Observable<SingleMealResponse>
    @GET("lookup.php?i=")
    fun singleMealnonObs(@Query("i") id: String): SingleMealResponse

}