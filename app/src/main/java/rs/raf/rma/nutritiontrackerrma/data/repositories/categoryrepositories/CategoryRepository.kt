package rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.categories.CategoryData
import rs.raf.rma.nutritiontrackerrma.data.models.Resource

interface CategoryRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Category>>
    fun getAllByPage(pageNumber: Int, pageSize: Int): Observable<List<Category>>
    fun getAllByName(name: String): Observable<List<Category>>
    fun insert(cat: CategoryData): Completable

    //getAllByName za filtriranje
}