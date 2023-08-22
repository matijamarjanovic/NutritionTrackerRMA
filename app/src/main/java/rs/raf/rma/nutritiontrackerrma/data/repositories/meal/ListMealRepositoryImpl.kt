package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.SavedMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.DateConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CaloriesService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.calories.Calorie
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SimpleMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import timber.log.Timber
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ListMealRepositoryImpl(
    private val localDataSource: ListMealDao,
    private val localDataSourceSaved: SavedMealDao,
    private val remoteDataSource: MealsService,
    private val remoteDataSourceCalories: CaloriesService
) : ListMealRepository {
    @SuppressLint("CheckResult")
    override fun fetchAllByArea(area:String): Observable<Resource<Unit>> {
        val observable1 = remoteDataSource.getAllMealsByArea(area)

        return observable1.flatMap { response ->
            val meals = response.meals
            val resultList = mutableListOf<SimpleMeal>()

            val entities = meals.map { meal ->
                var upit=""
                    remoteDataSource
                        .singleMeal(meal.idMeal.toString())
                        .map { response->
                                    val singleMeal=response.meals
                                    val pom=singleMeal.map {
                                        Meal(
                                            it.idMeal,
                                            it.strMeal,
                                            it.strCategory,
                                            it.strArea,
                                            it.strInstructions,
                                            it.strMealThumb,
                                            it.strTags,
                                            it.strYoutube,
                                            0.0,

                                            it.strIngredient1, it.strIngredient2, it.strIngredient3, it.strIngredient4, it.strIngredient5,
                                            it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strIngredient10,
                                            it.strIngredient11, it.strIngredient12, it.strIngredient13, it.strIngredient14, it.strIngredient15,
                                            it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient20,

                                            it.strMeasure1, it.strMeasure2, it.strMeasure3, it.strMeasure4, it.strMeasure5,
                                            it.strMeasure6, it.strMeasure7, it.strMeasure8, it.strMeasure9, it.strMeasure10,
                                            it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15,
                                            it.strMeasure16, it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure20
                                        )
                                    }
                                    upit =""+pom[0].strMeasure1+" "+pom[0].strIngredient1+" and "+pom[0].strMeasure2+" "+pom[0].strIngredient2+" and "+pom[0].strMeasure3+" "+pom[0].strIngredient3+" and "+pom[0].strMeasure4+" "+pom[0].strIngredient4+" and "+
                                    pom[0].strMeasure5+" "+pom[0].strIngredient5+" and "+pom[0].strMeasure6+" "+pom[0].strIngredient6+" and "+pom[0].strMeasure7+" "+pom[0].strIngredient7+" and "+pom[0].strMeasure8+" "+pom[0].strIngredient8+" and "+
                                    pom[0].strMeasure9+" "+pom[0].strIngredient9+" and "+pom[0].strMeasure10+" "+pom[0].strIngredient10+" and "+pom[0].strMeasure11+" "+pom[0].strIngredient11+" and "+pom[0].strMeasure12+" "+pom[0].strIngredient12+" and "+
                                    pom[0].strMeasure13+" "+pom[0].strIngredient13+" and "+pom[0].strMeasure14+" "+pom[0].strIngredient14+" and "+pom[0].strMeasure15+" "+pom[0].strIngredient15+" and "+pom[0].strMeasure16+" "+pom[0].strIngredient16+" and "+
                                    pom[0].strMeasure17+" "+pom[0].strIngredient17+" and "+pom[0].strMeasure18+" "+pom[0].strIngredient18+" and "+pom[0].strMeasure19+" "+pom[0].strIngredient19+" and "+pom[0].strMeasure20+" "+pom[0].strIngredient20
                        }

                //resultList.add(simpleMeal)
                ListMealEntity(
                    meal.idMeal,
                    meal.strMeal,
                    meal.strMealThumb,
                    0.0
                )
            }

            localDataSource.deleteAndInsertAll(entities)
            Observable.just(Resource.Success(Unit))

        }
    }

    override fun getCalories(meal: Meal): Observable<Double> {

        val upit =
            meal.strIngredient1 + " " + meal.strMeasure1 + " and " + meal.strIngredient2 + " " + meal.strMeasure2 + " " +
                    meal.strIngredient3 + " " + meal.strMeasure3 + " and " + meal.strIngredient4 + " " + meal.strMeasure4 + " " +
                    meal.strIngredient5 + " " + meal.strMeasure5 + " and " + meal.strIngredient6 + " " + meal.strMeasure6 + " " +
                    meal.strIngredient7 + " " + meal.strMeasure7 + " and " + meal.strIngredient8 + " " + meal.strMeasure8 + " " +
                    meal.strIngredient9 + " " + meal.strMeasure9 + " and " + meal.strIngredient10 + " " + meal.strMeasure10 + " " +
                    meal.strIngredient11 + " " + meal.strMeasure11 + " and " + meal.strIngredient12 + " " + meal.strMeasure12 + " " +
                    meal.strIngredient13 + " " + meal.strMeasure13 + " and " + meal.strIngredient14 + " " + meal.strMeasure14 + " " +
                    meal.strIngredient15 + " " + meal.strMeasure15 + " and " + meal.strIngredient16 + " " + meal.strMeasure16 + " " +
                    meal.strIngredient17 + " " + meal.strMeasure17 + " and " + meal.strIngredient18 + " " + meal.strMeasure18 + " " +
                    meal.strIngredient19 + " " + meal.strMeasure19 + " and " + meal.strIngredient20 + " " + meal.strMeasure20
        var result = upit.substringBefore("and     ")
        result = result.trim()

        return remoteDataSourceCalories.getCalories(result)
            .map { list ->
                list.map { it.calories }.sum()
            }
    }

    override fun fetchAllByCategory(category: String): Observable<Resource<Unit>> {

        return remoteDataSource
            .getAllMealsByCategory(category)
            .map { response ->
                // Extract the categories array from the ApiResponse
                val meals = response.meals

                // Save the categories to the local database
                val entities = meals.map {
                    ListMealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb,
                        0.0
                    )
                }
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                Resource.Success(Unit)
            }
    }

    override fun fetchAllByIngredient(ingredient: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllMealsByIngredient(ingredient)
            .map { response ->
                // Extract the categories array from the ApiResponse
                val meals = response.meals

                // Save the categories to the local database
                val entities = meals.map {
                    ListMealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb,
                        0.0

                    )
                }
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                Resource.Success(Unit)
            }    }

    override fun getSingleMeal(mealId: String): Observable<Meal> {
        return remoteDataSource
            .singleMeal(mealId)
            .map {  response ->
                val meals = response.meals

                val entities = meals.map {
                    Meal(
                        it.idMeal,
                        it.strMeal,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        0.0,

                        it.strIngredient1, it.strIngredient2, it.strIngredient3, it.strIngredient4, it.strIngredient5,
                        it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strIngredient10,
                        it.strIngredient11, it.strIngredient12, it.strIngredient13, it.strIngredient14, it.strIngredient15,
                        it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient20,

                        it.strMeasure1, it.strMeasure2, it.strMeasure3, it.strMeasure4, it.strMeasure5,
                        it.strMeasure6, it.strMeasure7, it.strMeasure8, it.strMeasure9, it.strMeasure10,
                        it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15,
                        it.strMeasure16, it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure20
                    )
                }
                    println("cccc ${entities[0].idMeal}")
                entities[0]
            }
    }

    override fun getSingleSavedMeal(mealName: String): Observable<List<SavedMeal>> {
        return localDataSourceSaved
            .getByName(mealName)
            .map { response ->
                var savedMeals = response.map {
                    SavedMeal(
                        it.idMeal,
                        it.strMeal,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strYoutube,
                        it.date,
                        it.whichMeal,
                        it.calories,
                        it.user,

                        it.strIngredient1, it.strIngredient2, it.strIngredient3, it.strIngredient4, it.strIngredient5,
                        it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strIngredient10,
                        it.strIngredient11, it.strIngredient12, it.strIngredient13, it.strIngredient14, it.strIngredient15,
                        it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient20,

                        it.strMeasure1, it.strMeasure2, it.strMeasure3, it.strMeasure4, it.strMeasure5,
                        it.strMeasure6, it.strMeasure7, it.strMeasure8, it.strMeasure9, it.strMeasure10,
                        it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15,
                        it.strMeasure16, it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure20
                    )
                    }
                savedMeals
                }

    }


    override fun getMealsIn7Days(day: String): Int {
        TODO("Not yet implemented")
    }

    override fun getAllMeals(): Observable<List<ListMeal>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    ListMeal(it.idMeal, it.strMeal,it.strMealThumb,it.calories)
                }
            }
    }

    override fun getAllSavedMeals(): Observable<List<SavedMeal>> {
        return localDataSourceSaved
            .getAll()
            .map {
                it.map {
                    println("datum "+it.date)
                    SavedMeal(
                            it.idMeal,
                            it.strMeal,
                            it.strCategory,
                            it.strArea,
                            it.strInstructions,
                            it.strMealThumb,
                            it.strYoutube,
                            it.date,
                            it.whichMeal,
                            it.calories,
                            it.user,

                            it.strIngredient1, it.strIngredient2, it.strIngredient3, it.strIngredient4, it.strIngredient5,
                            it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strIngredient10,
                            it.strIngredient11, it.strIngredient12, it.strIngredient13, it.strIngredient14, it.strIngredient15,
                            it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient20,

                            it.strMeasure1, it.strMeasure2, it.strMeasure3, it.strMeasure4, it.strMeasure5,
                            it.strMeasure6, it.strMeasure7, it.strMeasure8, it.strMeasure9, it.strMeasure10,
                            it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15,
                            it.strMeasure16, it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure20
                    )
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<ListMeal>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    ListMeal(it.idMeal, it.strMeal,it.strMealThumb,it.calories)
                }
            }
    }

    override fun getAllSavedByName(name: String): Observable<List<SavedMeal>> {
        return localDataSourceSaved
            .getByName(name)
            .map {
                it.map {
                    SavedMeal(
                        it.idMeal,
                        it.strMeal,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strYoutube,
                        it.date,
                        it.whichMeal,
                        it.calories,
                        it.user,

                        it.strIngredient1, it.strIngredient2, it.strIngredient3, it.strIngredient4, it.strIngredient5,
                        it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strIngredient10,
                        it.strIngredient11, it.strIngredient12, it.strIngredient13, it.strIngredient14, it.strIngredient15,
                        it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient20,

                        it.strMeasure1, it.strMeasure2, it.strMeasure3, it.strMeasure4, it.strMeasure5,
                        it.strMeasure6, it.strMeasure7, it.strMeasure8, it.strMeasure9, it.strMeasure10,
                        it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15,
                        it.strMeasure16, it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure20
                    )
                }
            }
    }
    @SuppressLint("CheckResult")
    override fun insert(meal: Meal, whichMeal:String, date: Date): Completable {

        val savedMeal = SavedMealEntity(
            meal.idMeal,
            meal.strMeal,
            meal.strCategory,
            meal.strArea,
            meal.strInstructions,
            meal.strMealThumb,
            meal.strYoutube,
            date,
            whichMeal,
            0.0,
            "user",

            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4, meal.strIngredient5,
            meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9, meal.strIngredient10,
            meal.strIngredient11, meal.strIngredient12, meal.strIngredient13, meal.strIngredient14, meal.strIngredient15,
            meal.strIngredient16, meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20,

            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5,
            meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10,
            meal.strMeasure11, meal.strMeasure12, meal.strMeasure13, meal.strMeasure14, meal.strMeasure15,
            meal.strMeasure16, meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
            )


        return localDataSourceSaved.insert(savedMeal)

    }

    override fun update(meal: SavedMeal, whichMeal: String, date: Date): Completable {

        val savedMeal = SavedMealEntity(
            meal.idMeal,
            meal.strMeal,
            meal.strCategory,
            meal.strArea,
            meal.strInstructions,
            meal.strMealThumb,
            meal.strYoutube,
            date,
            whichMeal,
            meal.calories,
            "user",

            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4, meal.strIngredient5,
            meal.strIngredient6, meal.strIngredient7, meal.strIngredient8, meal.strIngredient9, meal.strIngredient10,
            meal.strIngredient11, meal.strIngredient12, meal.strIngredient13, meal.strIngredient14, meal.strIngredient15,
            meal.strIngredient16, meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20,

            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4, meal.strMeasure5,
            meal.strMeasure6, meal.strMeasure7, meal.strMeasure8, meal.strMeasure9, meal.strMeasure10,
            meal.strMeasure11, meal.strMeasure12, meal.strMeasure13, meal.strMeasure14, meal.strMeasure15,
            meal.strMeasure16, meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
        )

        return localDataSourceSaved.update(savedMeal)
    }

    override fun delete(id: String): Completable {
        return localDataSourceSaved.delete(id)
    }
}


