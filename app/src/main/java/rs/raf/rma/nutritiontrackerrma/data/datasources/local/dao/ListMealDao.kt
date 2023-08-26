package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity

@Dao
abstract class ListMealDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: ListMealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<ListMealEntity>): Completable

    @Query("SELECT * FROM meals")
    abstract fun getAll(): Observable<List<ListMealEntity>>

    @Query("SELECT * FROM meals WHERE calories BETWEEN :minCalories AND :maxCalories ORDER BY calories DESC")
    abstract fun getAllSortedByCalDes(minCalories: Double, maxCalories: Double): Observable<List<ListMealEntity>>

    @Query("DELETE FROM meals")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<ListMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM meals WHERE strMeal LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<ListMealEntity>>
    @Query("SELECT * FROM meals WHERE strTags LIKE '%' || :searchString || '%' ORDER BY calories DESC")
    abstract fun getMealsContainingString(searchString: String): Observable<List<ListMealEntity>>

    @Query("DELETE FROM savedMeals WHERE idMeal = :id")
    abstract fun delete(id: String): Completable

    @Update
    abstract fun update(listMeal : ListMealEntity): Completable

}