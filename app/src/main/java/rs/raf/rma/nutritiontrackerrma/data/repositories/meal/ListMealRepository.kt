package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

interface ListMealRepository {
    fun fetchAllByArea(): Observable<ListMealResource<Unit>>

//    fun fetchAllByArea(area:String): Observable<ListMealResource<Unit>>
    fun fetchAllByCategory(category: String): Observable<ListMealResource<Unit>>
    fun fetchAllByIngredient(ingredient: String): Observable<ListMealResource<Unit>>

    fun getAllMeals(): Observable<List<ListMeal>>
    fun getAllByName(name: String): Observable<List<ListMeal>>
    fun insert(meal: ListMeal): Completable

    //fun insert(listMeal: ListMeal): Completable

}