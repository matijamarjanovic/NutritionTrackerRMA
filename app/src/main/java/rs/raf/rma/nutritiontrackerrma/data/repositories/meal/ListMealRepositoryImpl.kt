package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

class ListMealRepositoryImpl(
    private val localDataSource: ListMealDao,
    private val remoteDataSource: MealsService
) : ListMealRepository {

    override fun fetchAllByArea(area:String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllMealsByArea(area)
            .map { response ->
                // Extract the categories array from the ApiResponse
                val meals = response.meals

                // Save the categories to the local database
                val entities = meals.map {
                    ListMealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealThumb
                    )
                }
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                Resource.Success(Unit)
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
                        it.strMealThumb
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
                        it.strMealThumb
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

                entities[0]
            }
    }

    override fun getAllMeals(): Observable<List<ListMeal>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    ListMeal(it.idMeal, it.strMeal,it.strMealThumb)
                }
            }
    }

    override fun getAllByName(name: String): Observable<List<ListMeal>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    ListMeal(it.idMeal, it.strMeal,it.strMealThumb)
                }
            }
    }
    override fun insert(meal: ListMeal): Completable {
        val listMealEntity = ListMealEntity(meal.idMeal,meal.strMeal,meal.strMealThumb)
        return localDataSource
            .insert(listMealEntity)
    }




}