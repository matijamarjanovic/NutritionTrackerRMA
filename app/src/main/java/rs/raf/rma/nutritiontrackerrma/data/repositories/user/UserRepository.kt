package rs.raf.rma.nutritiontrackerrma.data.repositories.user

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.UserEntity
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.categories.CategoryData
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.models.user.User

interface UserRepository {

    fun insert(user: User): Completable
    fun checkUser(username:String):Observable<Int>
    fun getUser(username : String) : Observable<User>
    fun getAllByName(name: String): Observable<List<User>>
}