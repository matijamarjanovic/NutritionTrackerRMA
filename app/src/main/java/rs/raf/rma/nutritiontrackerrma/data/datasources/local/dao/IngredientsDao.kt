package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.IngredientEntity

@Dao
abstract class IngredientsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertIngredient(ingredientEntity: IngredientEntity) : Completable
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<IngredientEntity>): Completable
    @Query("SELECT * FROM ingredients")
    abstract fun getAll(): Observable<List<IngredientEntity>>
    @Query("SELECT * FROM ingredients ORDER BY strIngredient ASC")
    abstract fun getAllAscending(): Observable<List<IngredientEntity>>
    @Query("SELECT * FROM ingredients ORDER BY strIngredient DESC")
    abstract fun getAllDescending(): Observable<List<IngredientEntity>>
    @Query("SELECT * FROM ingredients WHERE strIngredient LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<IngredientEntity>>
    @Query("DELETE FROM itemFilters")
    abstract fun deleteAll()
    @Transaction
    open fun deleteAndInsertAll(entities: List<IngredientEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertAll2(entities: List<IngredientEntity>): Completable
    @Transaction
    open fun insertAll1(entities: List<IngredientEntity>) {
//        deleteAll()
        insertAll(entities).blockingAwait()
    }




}