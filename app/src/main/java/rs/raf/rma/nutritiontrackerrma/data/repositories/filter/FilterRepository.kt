package rs.raf.rma.nutritiontrackerrma.data.repositories.filter

import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter

import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseArea
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseCat
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseIng


interface FilterRepository {
    fun fetchAllAreas(): Observable<Resource<Unit>>
    fun fetchAllCategories(): Observable<Resource<Unit>>
    fun fetchAllIngredients(): Observable<Resource<Unit>>
    fun getAllAreas(): Observable<List<Filter>>
    fun getAllCategories(): Observable<List<Filter>>
    fun getAllIngredients(): Observable<List<Filter>>

    fun getAllByName(name: String): Observable<List<Filter>>
}