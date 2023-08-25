package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import android.annotation.SuppressLint

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListSingleMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.SavedMealDao

import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListSingleMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CaloriesService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.models.Resource

import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SimpleMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listSingleMeals.ListSingleMeal

import java.util.*
import kotlin.collections.ArrayList

class ListMealRepositoryImpl(
    private val localDataSource: ListMealDao,
    private val localDataSourceSaved: SavedMealDao,
    private val localDataSourceListSingleMeal: ListSingleMealDao,
    private val remoteDataSource: MealsService,
    private val remoteDataSourceCalories: CaloriesService,

) : ListMealRepository {



    @SuppressLint("CheckResult")
    override fun fetchAllByArea(area: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllMealsByArea(area)
            .flatMap { response ->
                // Extract the meals array from the ApiResponse
                val meals = response.meals

                // Create an observable for each meal entity
                val observables = meals.map { meal ->
                    remoteDataSource.singleMeal(meal.idMeal.toString()) // Replace with your actual second API call
                        .map { detailsResponse ->
                            val meals = detailsResponse.meals
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
                            val superMeal=entities[0]


                            val modifiedEntity = ListMealEntity(
                                superMeal.idMeal,
                                superMeal.strMeal,
                                superMeal.strCategory,
                                superMeal.strArea,
                                superMeal.strInstructions,
                                superMeal.strMealThumb,
                                superMeal.strTags,
                                superMeal.strYoutube,
                                0.0,

                                superMeal.strIngredient1, superMeal.strIngredient2, superMeal.strIngredient3, superMeal.strIngredient4, superMeal.strIngredient5,
                                superMeal.strIngredient6, superMeal.strIngredient7, superMeal.strIngredient8, superMeal.strIngredient9, superMeal.strIngredient10,
                                superMeal.strIngredient11, superMeal.strIngredient12, superMeal.strIngredient13, superMeal.strIngredient14, superMeal.strIngredient15,
                                superMeal.strIngredient16, superMeal.strIngredient17, superMeal.strIngredient18, superMeal.strIngredient19, superMeal.strIngredient20,

                                superMeal.strMeasure1, superMeal.strMeasure2, superMeal.strMeasure3, superMeal.strMeasure4, superMeal.strMeasure5,
                                superMeal.strMeasure6, superMeal.strMeasure7, superMeal.strMeasure8, superMeal.strMeasure9, superMeal.strMeasure10,
                                superMeal.strMeasure11, superMeal.strMeasure12, superMeal.strMeasure13, superMeal.strMeasure14, superMeal.strMeasure15,
                                superMeal.strMeasure16, superMeal.strMeasure17, superMeal.strMeasure18, superMeal.strMeasure19, superMeal.strMeasure20
                            )
                            modifiedEntity
                        }
                }

                // Use Observable.zip to combine the results of all observables
                Observable.zip(observables) { modifiedEntities ->
                    // Convert the array of modified entities to a list
                    val entityList = modifiedEntities.map { it as ListMealEntity }
                    entityList
                }
            }
            .doOnNext { modifiedEntities ->
                // Save the modified entities to the local database
                localDataSource.deleteAndInsertAll(modifiedEntities.toList())
            }
            .map {
                // Return a success resource
                Resource.Success(Unit)
            }
    }

    @SuppressLint("CheckResult")
    override fun getCalories(meals: ArrayList<Meal>): Observable<Double>? {

       for (meal in meals) {
            val upit = meal.strIngredient1 + " " + meal.strMeasure1 + " and " +
                    meal.strIngredient2 + " " + meal.strMeasure2 + " and " +
                    meal.strIngredient3 + " " + meal.strMeasure3 + " and " +
                    meal.strIngredient4 + " " + meal.strMeasure4 + " and " +
                    meal.strIngredient5 + " " + meal.strMeasure5 + " and " +
                    meal.strIngredient6 + " " + meal.strMeasure6 + " and " +
                    meal.strIngredient7 + " " + meal.strMeasure7 + " and " +
                    meal.strIngredient8 + " " + meal.strMeasure8 + " and " +
                    meal.strIngredient9 + " " + meal.strMeasure9 + " and " +
                    meal.strIngredient10 + " " + meal.strMeasure10 + " and " +
                    meal.strIngredient11 + " " + meal.strMeasure11 + " and " +
                    meal.strIngredient12 + " " + meal.strMeasure12 + " and " +
                    meal.strIngredient13 + " " + meal.strMeasure13 + " and " +
                    meal.strIngredient14 + " " + meal.strMeasure14 + " and " +
                    meal.strIngredient15 + " " + meal.strMeasure15 + " and " +
                    meal.strIngredient16 + " " + meal.strMeasure16 + " and " +
                    meal.strIngredient17 + " " + meal.strMeasure17 + " and " +
                    meal.strIngredient18 + " " + meal.strMeasure18 + " and " +
                    meal.strIngredient19 + " " + meal.strMeasure19 + " and " +
                    meal.strIngredient20 + " " + meal.strMeasure20


            var result = upit.substringBefore("and     ")
            result = result.trim()

            return remoteDataSourceCalories.getCalories(result)
                .map { list ->
                    list.map { it.calories }.sum()
                }
        }
        return null
    }

    override fun fetchAllByCategory(category: String): Observable<Resource<Unit>> {

        return remoteDataSource
            .getAllMealsByCategory(category)
            .flatMap { response ->
                // Extract the meals array from the ApiResponse
                val meals = response.meals

                // Create an observable for each meal entity
                val observables = meals.map { meal ->
                    remoteDataSource.singleMeal(meal.idMeal.toString()) // Replace with your actual second API call
                        .map { detailsResponse ->
                            val meals = detailsResponse.meals
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
                            val superMeal=entities[0]


                            val modifiedEntity = ListMealEntity(
                                superMeal.idMeal,
                                superMeal.strMeal,
                                superMeal.strCategory,
                                superMeal.strArea,
                                superMeal.strInstructions,
                                superMeal.strMealThumb,
                                superMeal.strTags,
                                superMeal.strYoutube,
                                0.0,

                                superMeal.strIngredient1, superMeal.strIngredient2, superMeal.strIngredient3, superMeal.strIngredient4, superMeal.strIngredient5,
                                superMeal.strIngredient6, superMeal.strIngredient7, superMeal.strIngredient8, superMeal.strIngredient9, superMeal.strIngredient10,
                                superMeal.strIngredient11, superMeal.strIngredient12, superMeal.strIngredient13, superMeal.strIngredient14, superMeal.strIngredient15,
                                superMeal.strIngredient16, superMeal.strIngredient17, superMeal.strIngredient18, superMeal.strIngredient19, superMeal.strIngredient20,

                                superMeal.strMeasure1, superMeal.strMeasure2, superMeal.strMeasure3, superMeal.strMeasure4, superMeal.strMeasure5,
                                superMeal.strMeasure6, superMeal.strMeasure7, superMeal.strMeasure8, superMeal.strMeasure9, superMeal.strMeasure10,
                                superMeal.strMeasure11, superMeal.strMeasure12, superMeal.strMeasure13, superMeal.strMeasure14, superMeal.strMeasure15,
                                superMeal.strMeasure16, superMeal.strMeasure17, superMeal.strMeasure18, superMeal.strMeasure19, superMeal.strMeasure20
                            )
                            modifiedEntity
                        }
                }

                // Use Observable.zip to combine the results of all observables
                Observable.zip(observables) { modifiedEntities ->
                    // Convert the array of modified entities to a list
                    val entityList = modifiedEntities.map { it as ListMealEntity }
                    entityList
                }
            }
            .doOnNext { modifiedEntities ->
                // Save the modified entities to the local database
                localDataSource.deleteAndInsertAll(modifiedEntities.toList())
            }
            .map {
                // Return a success resource
                Resource.Success(Unit)
            }
    }

    override fun fetchAllByIngredient(ingredient: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllMealsByIngredient(ingredient)
            .flatMap { response ->
                // Extract the meals array from the ApiResponse
                val meals = response.meals

                // Create an observable for each meal entity
                val observables = meals.map { meal ->
                    remoteDataSource.singleMeal(meal.idMeal.toString()) // Replace with your actual second API call
                        .map { detailsResponse ->
                            val meals = detailsResponse.meals
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
                            val superMeal=entities[0]


                            val modifiedEntity = ListMealEntity(
                                superMeal.idMeal,
                                superMeal.strMeal,
                                superMeal.strCategory,
                                superMeal.strArea,
                                superMeal.strInstructions,
                                superMeal.strMealThumb,
                                superMeal.strTags,
                                superMeal.strYoutube,
                                0.0,

                                superMeal.strIngredient1, superMeal.strIngredient2, superMeal.strIngredient3, superMeal.strIngredient4, superMeal.strIngredient5,
                                superMeal.strIngredient6, superMeal.strIngredient7, superMeal.strIngredient8, superMeal.strIngredient9, superMeal.strIngredient10,
                                superMeal.strIngredient11, superMeal.strIngredient12, superMeal.strIngredient13, superMeal.strIngredient14, superMeal.strIngredient15,
                                superMeal.strIngredient16, superMeal.strIngredient17, superMeal.strIngredient18, superMeal.strIngredient19, superMeal.strIngredient20,

                                superMeal.strMeasure1, superMeal.strMeasure2, superMeal.strMeasure3, superMeal.strMeasure4, superMeal.strMeasure5,
                                superMeal.strMeasure6, superMeal.strMeasure7, superMeal.strMeasure8, superMeal.strMeasure9, superMeal.strMeasure10,
                                superMeal.strMeasure11, superMeal.strMeasure12, superMeal.strMeasure13, superMeal.strMeasure14, superMeal.strMeasure15,
                                superMeal.strMeasure16, superMeal.strMeasure17, superMeal.strMeasure18, superMeal.strMeasure19, superMeal.strMeasure20
                            )
                            modifiedEntity
                        }
                }

                // Use Observable.zip to combine the results of all observables
                Observable.zip(observables) { modifiedEntities ->
                    // Convert the array of modified entities to a list
                    val entityList = modifiedEntities.map { it as ListMealEntity }
                    entityList
                }
            }
            .doOnNext { modifiedEntities ->
                // Save the modified entities to the local database
                localDataSource.deleteAndInsertAll(modifiedEntities.toList())
            }
            .map {
                // Return a success resource
                Resource.Success(Unit)
            }
    }

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
                entities[0]
            }
    }

    @SuppressLint("CheckResult")
    override fun getAllSingleMeals(meals: ArrayList<ListMeal>): Observable<Meal>? {

        var list : kotlin.collections.ArrayList<Meal> = ArrayList()
        for(meal in meals){
            return remoteDataSource
                .singleMeal(meal.idMeal.toString())
                .map { response ->
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

                            it.strIngredient1,
                            it.strIngredient2,
                            it.strIngredient3,
                            it.strIngredient4,
                            it.strIngredient5,
                            it.strIngredient6,
                            it.strIngredient7,
                            it.strIngredient8,
                            it.strIngredient9,
                            it.strIngredient10,
                            it.strIngredient11,
                            it.strIngredient12,
                            it.strIngredient13,
                            it.strIngredient14,
                            it.strIngredient15,
                            it.strIngredient16,
                            it.strIngredient17,
                            it.strIngredient18,
                            it.strIngredient19,
                            it.strIngredient20,

                            it.strMeasure1,
                            it.strMeasure2,
                            it.strMeasure3,
                            it.strMeasure4,
                            it.strMeasure5,
                            it.strMeasure6,
                            it.strMeasure7,
                            it.strMeasure8,
                            it.strMeasure9,
                            it.strMeasure10,
                            it.strMeasure11,
                            it.strMeasure12,
                            it.strMeasure13,
                            it.strMeasure14,
                            it.strMeasure15,
                            it.strMeasure16,
                            it.strMeasure17,
                            it.strMeasure18,
                            it.strMeasure19,
                            it.strMeasure20
                        )
                    }
                    entities[0]
                }
        }
        return null
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

//    ListMeal(
//    it.idMeal, it.strMeal,it.strCategory,it.strArea,it.strInstructions,it.strMealThumb,it.strTags,it.strYoutube,it.calories,
//    it.strIngredient1,it.strIngredient2,it.strIngredient3,it.strIngredient4,it.strIngredient5,it.strIngredient6,it.strIngredient7,it.strIngredient8,
//    it.strIngredient9,it.strIngredient10,it.strIngredient11,it.strIngredient12,it.strIngredient13,it.strIngredient14,it.strIngredient15,it.strIngredient16,
//    it.strIngredient17,it.strIngredient18,it.strIngredient19,it.strIngredient20,
//
//    it.strMeasure1,it.strMeasure2,it.strMeasure3,it.strMeasure4,it.strMeasure5,it.strMeasure6,it.strMeasure7,it.strMeasure8,it.strMeasure9,it.strMeasure10,
//    it.strMeasure11,it.strMeasure12,it.strMeasure13,it.strMeasure14,it.strMeasure15,it.strMeasure16,it.strMeasure17, it.strMeasure18,it.strMeasure19,it.strMeasure20
//    )
    override fun getAllMeals(): Observable<List<ListMeal>> {
    return localDataSource
        .getAll()
        .map {
            it.map {
                    ListMeal(
                    it.idMeal, it.strMeal,it.strCategory,it.strArea,it.strInstructions,it.strMealThumb,it.strTags,it.strYoutube,it.calories,
                    it.strIngredient1,it.strIngredient2,it.strIngredient3,it.strIngredient4,it.strIngredient5,it.strIngredient6,it.strIngredient7,it.strIngredient8,
                    it.strIngredient9,it.strIngredient10,it.strIngredient11,it.strIngredient12,it.strIngredient13,it.strIngredient14,it.strIngredient15,it.strIngredient16,
                    it.strIngredient17,it.strIngredient18,it.strIngredient19,it.strIngredient20,

                    it.strMeasure1,it.strMeasure2,it.strMeasure3,it.strMeasure4,it.strMeasure5,it.strMeasure6,it.strMeasure7,it.strMeasure8,it.strMeasure9,it.strMeasure10,
                    it.strMeasure11,it.strMeasure12,it.strMeasure13,it.strMeasure14,it.strMeasure15,it.strMeasure16,it.strMeasure17, it.strMeasure18,it.strMeasure19,it.strMeasure20
                    )
                }
            }
        }

    override fun clearListSingleMeals(): Completable {
            return localDataSourceListSingleMeal.deleteAll()
    }

    override fun getAllSavedMeals(usernmae:String): Observable<List<SavedMeal>> {
        return localDataSourceSaved
            .getAll(usernmae)
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
                    ListMeal(
                        it.idMeal, it.strMeal,it.strCategory,it.strArea,it.strInstructions,it.strMealThumb,it.strTags,it.strYoutube,it.calories,
                        it.strIngredient1,it.strIngredient2,it.strIngredient3,it.strIngredient4,it.strIngredient5,it.strIngredient6,it.strIngredient7,it.strIngredient8,
                        it.strIngredient9,it.strIngredient10,it.strIngredient11,it.strIngredient12,it.strIngredient13,it.strIngredient14,it.strIngredient15,it.strIngredient16,
                        it.strIngredient17,it.strIngredient18,it.strIngredient19,it.strIngredient20,

                        it.strMeasure1,it.strMeasure2,it.strMeasure3,it.strMeasure4,it.strMeasure5,it.strMeasure6,it.strMeasure7,it.strMeasure8,it.strMeasure9,it.strMeasure10,
                        it.strMeasure11,it.strMeasure12,it.strMeasure13,it.strMeasure14,it.strMeasure15,it.strMeasure16,it.strMeasure17, it.strMeasure18,it.strMeasure19,it.strMeasure20
                        )
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
        val sharedPreferencesManager = SharedPreferencesManager.getInstance()
        val username=sharedPreferencesManager.getUsername()?:""
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
            username,

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
            meal.user,

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

    override fun getMealsIn7Days(day: String): Int {
        TODO("Not yet implemented")
    }

}


