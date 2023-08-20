package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.reactivex.Completable
import io.reactivex.Observable
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
        val observable2 = remoteDataSourceCalories.getCalories("1lb brisket and fries")
        return Observable.zip(
            observable1,observable2
        ){ response1, response2 ->
            val meals1 = response1.meals
            val resultList = mutableListOf<SimpleMeal>()

            val entities = meals1.map { meal ->
                remoteDataSource.singleMeal(meal.idMeal.toString())
                    .subscribeOn(Schedulers.io())
                    .subscribe { response ->
                        val meals = response.meals
                        if (meals.isNotEmpty()) {
                            val mealItem = meals[0]
                            val simpleMeal = SimpleMeal(
                                mealItem.idMeal,
                                mealItem.strMeal ?: "",
                                mealItem.strIngredient1 ?: "",
                                mealItem.strIngredient2 ?: "",
                                mealItem.strIngredient3 ?: "",
                                mealItem.strIngredient4 ?: "",
                                mealItem.strIngredient5 ?: "",
                                mealItem.strIngredient6 ?: "",
                                mealItem.strIngredient7 ?: "",
                                mealItem.strIngredient8 ?: "",
                                mealItem.strIngredient9 ?: "",
                                mealItem.strIngredient10 ?: "",
                                mealItem.strIngredient11 ?: "",
                                mealItem.strIngredient12 ?: "",
                                mealItem.strIngredient13 ?: "",
                                mealItem.strIngredient14 ?: "",
                                mealItem.strIngredient15 ?: "",
                                mealItem.strIngredient16 ?: "",
                                mealItem.strIngredient17 ?: "",
                                mealItem.strIngredient18 ?: "",
                                mealItem.strIngredient19 ?: "",
                                mealItem.strIngredient20 ?: "",

                                mealItem.strMeasure1 ?: "",
                                mealItem.strMeasure2 ?: "",
                                mealItem.strMeasure3 ?: "",
                                mealItem.strMeasure4 ?: "",
                                mealItem.strMeasure5 ?: "",
                                mealItem.strMeasure6 ?: "",
                                mealItem.strMeasure7 ?: "",
                                mealItem.strMeasure8 ?: "",
                                mealItem.strMeasure9 ?: "",
                                mealItem.strMeasure10 ?: "",
                                mealItem.strMeasure11 ?: "",
                                mealItem.strMeasure12 ?: "",
                                mealItem.strMeasure13 ?: "",
                                mealItem.strMeasure14 ?: "",
                                mealItem.strMeasure15 ?: "",
                                mealItem.strMeasure16 ?: "",
                                mealItem.strMeasure17 ?: "",
                                mealItem.strMeasure18 ?: "",
                                mealItem.strMeasure19 ?: "",
                                mealItem.strMeasure20 ?: ""
                            )
                            val upit =
                                simpleMeal.strIngredient1 + " " + mealItem.strMeasure1 + " and " + simpleMeal.strIngredient2 + " " + mealItem.strMeasure2 + " " +
                                        simpleMeal.strIngredient3 + " " + mealItem.strMeasure3 + " and " + simpleMeal.strIngredient4 + " " + mealItem.strMeasure4 + " " +
                                        simpleMeal.strIngredient5 + " " + mealItem.strMeasure5 + " and " + simpleMeal.strIngredient6 + " " + mealItem.strMeasure6 + " " +
                                        simpleMeal.strIngredient7 + " " + mealItem.strMeasure7 + " and " + simpleMeal.strIngredient8 + " " + mealItem.strMeasure8 + " " +
                                        simpleMeal.strIngredient9 + " " + mealItem.strMeasure9 + " and " + simpleMeal.strIngredient10 + " " + mealItem.strMeasure10 + " " +
                                        simpleMeal.strIngredient11 + " " + mealItem.strMeasure11 + " and " + simpleMeal.strIngredient12 + " " + mealItem.strMeasure12 + " " +
                                        simpleMeal.strIngredient13 + " " + mealItem.strMeasure13 + " and " + simpleMeal.strIngredient14 + " " + mealItem.strMeasure14 + " " +
                                        simpleMeal.strIngredient15 + " " + mealItem.strMeasure15 + " and " + simpleMeal.strIngredient16 + " " + mealItem.strMeasure16 + " " +
                                        simpleMeal.strIngredient17 + " " + mealItem.strMeasure17 + " and " + simpleMeal.strIngredient18 + " " + mealItem.strMeasure18 + " " +
                                        simpleMeal.strIngredient19 + " " + mealItem.strMeasure19 + " and " + simpleMeal.strIngredient20 + " " + mealItem.strMeasure20
                            var result = upit.substringBefore("and     ")
                            result = result.trim()

                            //println("222 UPIT:= "+result)
                            //println("111"+simpleMeal.idMeal+simpleMeal.strMeal+"ING : "+simpleMeal.strIngredient1)
                            resultList.add(simpleMeal)

                            remoteDataSourceCalories.getCalories(result).map {
                                it.map { response ->
                                    Calorie(
                                        name = response.name,
                                        calories = response.calories,
                                    )
                                    println("sadsdasdad" + response.name + response.calories)
                                }
                            }
                        }
                    }

                ListMealEntity(
                    meal.idMeal,
                    meal.strMeal,
                    meal.strMealThumb,
                    0.0
                )
            }

            localDataSource.deleteAndInsertAll(entities)
            Resource.Success(Unit)
        }
    }

    override fun getCalories(list: String): Int {

        return 0
    }
//    override fun fetchAllByArea(area: String): Observable<Resource<Unit>> {
//        return remoteDataSource
//            .getAllMealsByArea(area)
//            .flatMap { response ->
//                val meals = response.meals
//
//                val entities = meals.map {
//                    ListMealEntity(
//                        it.idMeal,
//                        it.strMeal,
//                        it.strMealThumb,
//                        0.0
//                    )
//                }
//
//                val singleMealObservables = entities.map { entity ->
//                    remoteDataSource.singleMeal(entity.idMeal.toString())
//                }
//
//                Observable.merge(singleMealObservables)
//                    .toList()
//                    .flatMap { singleMealResponses ->
//                        val updatedEntities = entities.mapIndexed { index, entity ->
//                            val singleMealResponse = singleMealResponses[index]
//                            // Process singleMealResponse and update entity accordingly
//                            entity.copy(someProperty = singleMealResponse.someValue)
//                        }
//                        localDataSource.deleteAndInsertAll(updatedEntities)
//                        Observable.just(Resource.Success(Unit))
//                    }
//            }
//            .startWith(Resource.Loading) // Indicate that the operation is in progress
//            .onErrorReturn { e ->
//                Resource.Error(e.message ?: "An error occurred")
//            }
//    }
//    override fun fetchAllByArea(area: String): Observable<Resource<Unit>> {
//        return remoteDataSource
//            .getAllMealsByArea(area)
//            .map { response ->
//                // Extract the categories array from the ApiResponse
//                val meals = response.meals
//
//                // Save the categories to the local database
//                val entities = meals.map {
//                    ListMealEntity(
//                        it.idMeal,
//                        it.strMeal,
//                        it.strMealThumb,
//                        0.0
//                    )
//                }
//
//               for(i in entities){
//
//                   remoteDataSource.singleMeal(i.idMeal.toString())
//                        .subscribeOn(Schedulers.io())
//                        .map{ response ->
//                            val meals = response.meals
//                            if (meals.isNotEmpty()) {
//                                val mealItem = meals[0]
//                                val simpleMeal = SimpleMeal(
//                                    mealItem.idMeal,
//                                    mealItem.strMeal ?: "",
//                                    mealItem.strIngredient1 ?: "", mealItem.strIngredient2 ?: "", mealItem.strIngredient3 ?: "",
//                                    mealItem.strIngredient4 ?: "", mealItem.strIngredient5 ?: "", mealItem.strIngredient6 ?: "",
//                                    mealItem.strIngredient7 ?: "", mealItem.strIngredient8 ?: "", mealItem.strIngredient9 ?: "",
//                                    mealItem.strIngredient10 ?: "", mealItem.strIngredient11 ?: "", mealItem.strIngredient12 ?: "",
//                                    mealItem.strIngredient13 ?: "", mealItem.strIngredient14 ?: "", mealItem.strIngredient15 ?: "",
//                                    mealItem.strIngredient16 ?: "", mealItem.strIngredient17 ?: "", mealItem.strIngredient18 ?: "",
//                                    mealItem.strIngredient19 ?: "", mealItem.strIngredient20 ?: "",
//
//                                    mealItem.strMeasure1 ?: "", mealItem.strMeasure2 ?: "", mealItem.strMeasure3 ?: "",
//                                    mealItem.strMeasure4 ?: "", mealItem.strMeasure5 ?: "", mealItem.strMeasure6 ?: "",
//                                    mealItem.strMeasure7 ?: "", mealItem.strMeasure8 ?: "", mealItem.strMeasure9 ?: "",
//                                    mealItem.strMeasure10 ?: "", mealItem.strMeasure11 ?: "", mealItem.strMeasure12 ?: "",
//                                    mealItem.strMeasure13 ?: "", mealItem.strMeasure14 ?: "", mealItem.strMeasure15 ?: "",
//                                    mealItem.strMeasure16 ?: "", mealItem.strMeasure17 ?: "", mealItem.strMeasure18 ?: "",
//                                    mealItem.strMeasure19 ?: "", mealItem.strMeasure20 ?: ""
//                                )
//                                val upit =    simpleMeal.strIngredient1+" "+mealItem.strMeasure1+" and "+simpleMeal.strIngredient2+" "+mealItem.strMeasure2+" "+
//                                              simpleMeal.strIngredient3+" "+mealItem.strMeasure3+" and "+simpleMeal.strIngredient4+" "+mealItem.strMeasure4+" "+
//                                              simpleMeal.strIngredient5+" "+mealItem.strMeasure5+" and "+simpleMeal.strIngredient6+" "+mealItem.strMeasure6+" "+
//                                              simpleMeal.strIngredient7+" "+mealItem.strMeasure7+" and "+simpleMeal.strIngredient8+" "+mealItem.strMeasure8+" "+
//                                              simpleMeal.strIngredient9+" "+mealItem.strMeasure9+" and "+simpleMeal.strIngredient10+" "+mealItem.strMeasure10+" "+
//                                              simpleMeal.strIngredient11+" "+mealItem.strMeasure11+" and "+simpleMeal.strIngredient12+" "+mealItem.strMeasure12+" "+
//                                              simpleMeal.strIngredient13+" "+mealItem.strMeasure13+" and "+simpleMeal.strIngredient14+" "+mealItem.strMeasure14+" "+
//                                              simpleMeal.strIngredient15+" "+mealItem.strMeasure15+" and "+simpleMeal.strIngredient16+" "+mealItem.strMeasure16+" "+
//                                              simpleMeal.strIngredient17+" "+mealItem.strMeasure17+" and "+simpleMeal.strIngredient18+" "+mealItem.strMeasure18+" "+
//                                              simpleMeal.strIngredient19+" "+mealItem.strMeasure19+" and "+simpleMeal.strIngredient20+" "+mealItem.strMeasure20
//                                var result = upit.substringBefore("and     ")
//                                result=result.trim()
//                                println(result)
//                            }
//                        }
//
//
//
//                   i.calories=1.1
//                   println(i.strMeal+" "+i.calories+" aa=")
//               }
//                localDataSource.deleteAndInsertAll(entities)
//
//                Resource.Success(Unit)
//            }
//    }


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

    override fun update(meal: Meal, whichMeal: String, date: Date): Completable {

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

        return localDataSourceSaved.update(savedMeal)
    }

    override fun delete(id: String): Completable {
        return localDataSourceSaved.delete(id)
    }
}


