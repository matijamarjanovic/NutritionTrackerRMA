package rs.raf.rma.nutritiontrackerrma.data.repositories.filter

import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.FilterDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.IngredientsDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.IngredientEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CaloriesService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.FilterService
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import timber.log.Timber


class FilterRepositoryImpl(

    private val localDataSource: FilterDao,
    private val remoteDataSource: FilterService,
    private val remoteDataSourceCalories: CaloriesService,
    private val localDataSourceIng: IngredientsDao

    ) : FilterRepository {
    override fun fetchAllAreas(): Observable<Resource<Unit>> {
        return remoteDataSource
        .getAllAreas()
        .map { response ->
            val items = response.meals
            Timber.e(items.toString())

            val entities = items.map {
                FilterEntity(
                    0,
                    it.name
                )
            }

            localDataSource.deleteAndInsertAll(entities)
            // Return a success resource
            Resource.Success(Unit)
        }
    }

    override fun fetchAllCategories(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllCategories()
            .map { response ->
                val items = response.meals

                val entities = items.map {
                    FilterEntity(
                        0,
                        it.name
                    )
                }

                localDataSource.deleteAndInsertAll(entities)
                // Return a success resource
                Resource.Success(Unit)
            }
    }

    override fun fetchAllIngredients(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllIngredients()
            .map { response ->
                val items = response.meals

                val entities = items.map {
                    FilterEntity(
                        0,
                        it.name
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
                // Return a success resource
                Resource.Success(Unit)
            }
    }

    override fun getAllAreas(): Observable<List<Filter>> {

            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }

    }

    override fun getAllCategories(): Observable<List<Filter>> {


            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }

    }

    override fun getAllIngredients(): Observable<List<Filter>> {

            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }

    }

    override fun getAllAreasAscending(): Observable<List<Filter>> {
        return localDataSource
            .getAllAscending()
            .map {
                it.map {
                    Filter(it.name)
                }
            }
    }

    override fun getAllCategoriesAscending(): Observable<List<Filter>> {
        return localDataSource
            .getAllAscending()
            .map {
                it.map {
                    Filter(it.name)
                }
            }
    }

    override fun getAllIngredientsAscending(): Observable<List<Filter>> {
        return localDataSource
            .getAllAscending()
            .map {
                it.map {
                    Filter(it.name)
                }
            }
    }

    override fun getAllAreasDescending(): Observable<List<Filter>> {
        return localDataSource
            .getAllDescending()
            .map {
                it.map {
                    Filter(it.name)
                }
            }
    }

    override fun getAllCategoriesDescending(): Observable<List<Filter>> {
        return localDataSource
            .getAllDescending()
            .map {
                it.map {
                    Filter(it.name)
                }
            }
    }

    override fun getAllIngredientsDescending(): Observable<List<Filter>> {
        return localDataSource
            .getAllDescending()
            .flatMap { localIngredients ->
                // Define a batch size to limit the number of ingredients in each request
                val batchSize = 29 // You can adjust this as needed

                // Split the list of ingredients into batches
                val ingredientBatches = localIngredients.chunked(batchSize)
                var pom=    ""
                for (i in localIngredients){
                    pom=pom+"100g "+i.name+", "

                    }
                println(" 11 "+pom)
                // Create an Observable to emit batches of ingredients
                val batchObservable = Observable.fromIterable(ingredientBatches)

                // For each batch, make an API request
                batchObservable.flatMap { batch ->
                    // Extract ingredient names from the batch
                    val ingredientNames = batch.map { "100g ${it.name}" }

                    // Concatenate the ingredient names into a single string, separated by a delimiter
                    val concatenatedNames = ingredientNames.joinToString(",")

                    // Make an API request for the batch of ingredients
                    remoteDataSourceCalories.getCalories(concatenatedNames)
                        .map { calorieResponse ->
                            val filters = mutableListOf<Filter>()

                            for ((index, localIng) in batch.withIndex()) {
                                val calorie = if (index < calorieResponse.size) {
                                    // Use index to access elements in calorieResponse
                                    calorieResponse[index].calories
                                } else {
                                    0.0 // Set a default value for calorie when calorieResponse is empty or doesn't have enough elements
                                }

                                val ingredientToAdd = IngredientEntity(
                                    localIng.id.toString(),
                                    localIng.name,
                                    100,
                                    calorie
                                )

                                println("calorie response for ${localIng.name}: $calorie")

                                filters.add(Filter(localIng.name))
                            }
                            filters
                        }
                }
            }
    }


    override fun getAllByName(name: String): Observable<List<Filter>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Filter(it.name)
                }
            }
    }


}