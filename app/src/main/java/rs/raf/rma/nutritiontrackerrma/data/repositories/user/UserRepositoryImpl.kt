package rs.raf.rma.nutritiontrackerrma.data.repositories.user

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import retrofit2.HttpException
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.UserDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.UserEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CaloriesService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.calories.Calorie
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SimpleMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMealResponse
import rs.raf.rma.nutritiontrackerrma.data.models.meals.singleMeals.SingleMealResponse
import rs.raf.rma.nutritiontrackerrma.data.models.user.User
import timber.log.Timber

class UserRepositoryImpl(
    private val localDataSource: UserDao,
) : UserRepository {
    override fun insert(user: User): Completable {
        val userEntity=UserEntity(user.username,user.password)
        return localDataSource.insert(userEntity)
    }

    override fun checkUser(username: String): Observable<Int> {
        return localDataSource.countUsersWithUsername(username)
    }


    override fun getUser(username: String): Observable<User> {
        return localDataSource
            .getUserByUsernameAndPassword(username)
            .map { response->
                User(response.username,response.password)
            }
    }


    override fun getAllByName(name: String): Observable<List<User>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    User(it.username, it.password)
                }
            }
    }



}