package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import java.util.Date

interface ListMealRepository {
    fun fetchAllByArea(area: String): Observable<Resource<Unit>>
    fun fetchAllByCategory(category: String): Observable<Resource<Unit>>
    fun fetchAllByIngredient(ingredient: String): Observable<Resource<Unit>>
    fun getAllMeals(): Observable<List<ListMeal>>
    fun getAllByName(name: String): Observable<List<ListMeal>>
    fun getAllSavedMeals(): Observable<List<SavedMeal>>
    fun getAllSavedByName(name: String): Observable<List<SavedMeal>>
    fun insert(meal: Meal, whichMeal :String, date: Date): Completable
    fun update(meal: SavedMeal, whichMeal :String, date: Date): Completable
    fun delete(id :String): Completable
    fun getSingleMeal(mealId : String) : Observable<Meal>
    fun getCalories(list:String):Int
    fun getMealsIn7Days(day:String):Int
    //fun insert(listMeal: ListMeal): Completable
}