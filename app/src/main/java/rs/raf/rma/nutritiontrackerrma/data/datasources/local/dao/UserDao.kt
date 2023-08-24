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

    @Query("SELECT * FROM users WHERE username LIKE :name || '%'")
    abstract fun getByName(name: String): Observable<List<UserEntity>>

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(user: UserEntity): Completable

    @Query("SELECT * FROM users WHERE username = :username ")
    abstract fun getUserByUsernameAndPassword(username: String): Observable<UserEntity>

    @Query("SELECT COUNT(*) FROM users WHERE username = :username")
    abstract fun countUsersWithUsername(username: String): Observable<Int>
}