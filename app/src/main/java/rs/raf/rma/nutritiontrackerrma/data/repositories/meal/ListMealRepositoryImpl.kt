package rs.raf.rma.nutritiontrackerrma.data.repositories.meal

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.models.ListMealResource
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

class ListMealRepositoryImpl(
    private val localDataSource: ListMealDao,
    private val remoteDataSource: MealsService
) : ListMealRepository {

    override fun fetchAllByArea(): Observable<ListMealResource<Unit>> {
        return remoteDataSource
            .getAllMealsByArea()
            .map { response ->
                // Extract the categories array from the ApiResponse
                val meals = response.listMeals

                // Save the categories to the local database
                val entities = meals.map {
                    ListMealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealTumb
                    )
                }
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                ListMealResource.Success(Unit)
            }
    }



    override fun fetchAllByCategory(category: String): Observable<ListMealResource<Unit>> {
        return remoteDataSource
            .getAllMealsByArea()
            .map { response ->
                // Extract the categories array from the ApiResponse
                val meals = response.listMeals

                // Save the categories to the local database
                val entities = meals.map {
                    ListMealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealTumb
                    )
                }
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                ListMealResource.Success(Unit)
            }    }

    override fun fetchAllByIngredient(ingredient: String): Observable<ListMealResource<Unit>> {
        return remoteDataSource
            .getAllMealsByIngredient(ingredient)
            .map { response ->
                // Extract the categories array from the ApiResponse
                val meals = response.listMeals

                // Save the categories to the local database
                val entities = meals.map {
                    ListMealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strMealTumb
                    )
                }
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                ListMealResource.Success(Unit)
            }    }

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
        val listMealEntity = ListMealEntity(meal.idMeal,meal.strMeal,meal.strMealTumb)
        return localDataSource
            .insert(listMealEntity)
    }
//    override fun insert(meal: SingleMeal): Completable {
////        val singleMealEntity = SingleMealEntity(meal.idMeal,meal.strMeal,meal.strCategory,meal.strArea,
////                                meal.strInstructions,meal.strMealTumb,meal.strTags,meal.strYoutube)
////        return localDataSource
////            .insert(singleMealEntity)
//    }


}