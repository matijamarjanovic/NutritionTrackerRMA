package rs.raf.rma.nutritiontrackerrma.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rma.nutritiontrackerrma.data.models.calories.CaloriesResponse
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMealResponse

interface CaloriesService {
    @GET("nutrition")
    fun getCalories(@Query("query") query: String?): Observable<List<CaloriesResponse>>

    @GET("nutrition")
    fun getCaloriesnonObs(@Query("query") query: String?): List<CaloriesResponse>
}