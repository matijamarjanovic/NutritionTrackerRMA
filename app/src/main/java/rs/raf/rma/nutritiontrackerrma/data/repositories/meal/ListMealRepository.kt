package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

interface ListMealRepository {
    fun fetchAllByArea(area: String): Observable<Resource<Unit>>
    fun fetchAllByCategory(category: String): Observable<Resource<Unit>>
    fun fetchAllByIngredient(ingredient: String): Observable<Resource<Unit>>

    fun getAllMeals(): Observable<List<ListMeal>>
    fun getAllByName(name: String): Observable<List<ListMeal>>
    fun insert(meal: ListMeal): Completable

    fun getSingleMeal(mealId : String) : Observable<Meal>


    //fun insert(listMeal: ListMeal): Completable

}