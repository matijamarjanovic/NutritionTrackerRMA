package rs.raf.rma.nutritiontrackerrma.data.repositories.mealStatistics

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import java.util.Date

interface MealStatisticsRepository {
    fun getMealsIn7Days(user:String,days: List<String>):Observable<List<Int>>
    fun getMealsIn7DaysByCalories(user:String,days: List<String>):Observable<List<Int>>

}