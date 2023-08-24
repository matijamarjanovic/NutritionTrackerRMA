package rs.raf.rma.nutritiontrackerrma.data.repositories.mealStatistics

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.SavedMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.DateConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CaloriesService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.calories.Calorie
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SimpleMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import timber.log.Timber
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class MealStatisticsRepositoryImpl(
    private val localDataSource: SavedMealDao,
) : MealStatisticsRepository {
    override fun getMealsIn7Days(user:String,days: List<String>): Observable<List<Int>> {
        val observables = days.map { day ->
            localDataSource.getMealsInDay(user,day)
                .doOnNext { count ->
                    println("Meal count for $day: $count user:$user")
                }
        }
        return Observable.combineLatest(observables) { results ->
            results.map { it as Int }
        }
    }

    override fun getMealsIn7DaysByCalories(user:String,days: List<String>): Observable<List<Int>> {
        val observables = days.map { day ->
            localDataSource.getCaloriesInDay(user, day)
                .map { count ->
                    // Convert the Double count to Int
                    Math.round(count).toInt()
                }
                .doOnNext { count ->
                    println("Meal count for $day: $count")
                }
        }
        return Observable.combineLatest(observables) { results ->
            results.map { it as Int }
        }
    }
}




