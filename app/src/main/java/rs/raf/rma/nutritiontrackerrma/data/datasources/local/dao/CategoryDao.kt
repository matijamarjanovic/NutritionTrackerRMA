package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity

@Dao
abstract class CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCategory(categoryEntity: CategoryEntity) : Completable

    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>


}