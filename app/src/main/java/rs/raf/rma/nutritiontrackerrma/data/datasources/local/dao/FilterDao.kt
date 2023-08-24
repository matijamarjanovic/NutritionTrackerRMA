package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity

@Dao
abstract class FilterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategory(categoryEntity: FilterEntity) : Completable
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<FilterEntity>): Completable
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entitie: FilterEntity) : Completable

    @Query("SELECT * FROM itemFilters")
    abstract fun getAll(): Observable<List<FilterEntity>>

    @Query("SELECT * FROM itemFilters ORDER BY name ASC")
    abstract fun getAllAscending(): Observable<List<FilterEntity>>

    @Query("SELECT * FROM itemFilters ORDER BY name DESC")
    abstract fun getAllDescending(): Observable<List<FilterEntity>>

    @Query("SELECT * FROM itemFilters WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<FilterEntity>>


    @Query("DELETE FROM itemFilters")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<FilterEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }




}