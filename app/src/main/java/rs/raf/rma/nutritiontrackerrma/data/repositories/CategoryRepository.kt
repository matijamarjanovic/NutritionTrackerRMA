package rs.raf.rma.nutritiontrackerrma.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.Resource

interface CategoryRepository {

    fun fetchAll(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<Category>>
    fun insert(cat: Category): Completable

    //getAllByName za filtriranje
}