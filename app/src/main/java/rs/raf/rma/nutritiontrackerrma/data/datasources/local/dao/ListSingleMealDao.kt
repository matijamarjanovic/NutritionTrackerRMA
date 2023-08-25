package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListSingleMealEntity

@Dao
abstract class ListSingleMealDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: ListSingleMealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<ListSingleMealEntity>): Completable

    @Query("SELECT * FROM listSingleMeals")
    abstract fun getAll(): Observable<List<ListSingleMealEntity>>

    @Query("DELETE FROM listSingleMeals")
    abstract fun deleteAll(): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<ListSingleMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM listSingleMeals WHERE strMeal LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<ListSingleMealEntity>>
    @Query("DELETE FROM listSingleMeals WHERE idMeal = :id")
    abstract fun delete(id: String): Completable
    @Update
    abstract fun update(listSingleMeal : ListSingleMealEntity): Completable

}