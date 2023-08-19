package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.WeekEntity

@Dao
abstract class MealStatisticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMealIntoWeak(weekEntity: WeekEntity) : Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<WeekEntity>): Completable

    @Query("SELECT * FROM week")
    abstract fun getAll(): Observable<List<WeekEntity>>

    @Query("SELECT * FROM week WHERE day LIKE :day || '%' ")
    abstract fun getAllforDay(day: String): Observable<List<WeekEntity>>

    @Query("SELECT * FROM categories WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<WeekEntity>>

    @Query("DELETE FROM categories")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<WeekEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }




}