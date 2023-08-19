package rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.UserEntity
import rs.raf.rma.nutritiontrackerrma.data.models.user.User

@Dao
abstract class UserDao {


    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    abstract fun getUserByUsernameAndPassword(username: String, password: String): Observable<List<User>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entities: UserEntity): Completable


}