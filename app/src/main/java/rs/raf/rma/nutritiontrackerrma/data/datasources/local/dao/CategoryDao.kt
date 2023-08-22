package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategory(categoryEntity: CategoryEntity) : Completable
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<CategoryEntity>): Completable

    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>

    @Query("SELECT * FROM categories LIMIT :pageSize OFFSET :pageNumber * :pageSize")
    abstract fun getCategoriesByPage(pageNumber: Int, pageSize: Int): Observable<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<CategoryEntity>>

    @Query("DELETE FROM categories")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<CategoryEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }




}