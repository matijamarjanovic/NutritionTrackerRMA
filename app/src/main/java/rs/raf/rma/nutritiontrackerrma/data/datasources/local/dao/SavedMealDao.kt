package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity

@Dao
abstract class SavedMealDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: SavedMealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<SavedMealEntity>): Completable

    @Query("SELECT * FROM savedMeals WHERE user LIKE :user")
    abstract fun getAll(user:String): Observable<List<SavedMealEntity>>

    @Query("DELETE FROM savedMeals")
    abstract fun deleteAll()

    @Query("DELETE FROM savedMeals WHERE idMeal = :id")
    abstract fun delete(id: String): Completable
    @Update
    abstract fun update(entity: SavedMealEntity): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<SavedMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
    @Query("SELECT * FROM savedMeals WHERE strMeal LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<SavedMealEntity>>

    @Query("SELECT COUNT(*) FROM savedMeals WHERE strftime('%Y-%m-%d', date / 1000, 'unixepoch', 'localtime') = :date AND user = :user")
    abstract fun getMealsInDay(user: String, date: String): Observable<Int>
    @Query("SELECT COALESCE(SUM(calories), 0.0) FROM savedMeals WHERE strftime('%Y-%m-%d', date / 1000, 'unixepoch', 'localtime') = :date AND user = :user")
    abstract fun getCaloriesInDay(user: String, date: String): Observable<Double>

}