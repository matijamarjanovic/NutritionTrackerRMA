package rs.raf.rma.nutritiontrackerrma.data.repositories.ingredients

import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter

import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseArea
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseCat
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseIng
import rs.raf.rma.nutritiontrackerrma.data.models.ingredients.Ingredient


interface IngredientsRepository {

    fun fetchAllIngredients(): Observable<Resource<Unit>>
    fun getAllIngredients(): Observable<List<Ingredient>>
    fun getAllIngredientsAscending(): Observable<List<Ingredient>>
    fun getAllIngredientsDescending(): Observable<List<Ingredient>>
    fun getAllByName(name: String): Observable<List<Ingredient>>
}