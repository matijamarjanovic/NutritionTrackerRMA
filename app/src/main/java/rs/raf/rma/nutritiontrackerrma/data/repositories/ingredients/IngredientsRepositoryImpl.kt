package rs.raf.rma.nutritiontrackerrma.data.repositories.ingredients

import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.FilterDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.IngredientsDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.IngredientEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.FilterService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.IngredientsService
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import rs.raf.rma.nutritiontrackerrma.data.models.ingredients.Ingredient
import timber.log.Timber


class IngredientsRepositoryImpl(

    private val localDataSource: IngredientsDao,
    private val remoteDataSource: IngredientsService

) : IngredientsRepository {


    override fun fetchAllIngredients(): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllIngredients()
            .map { response ->
                val items = response.meals

                val entities = items.map {
                    IngredientEntity (
                        it.idIngredient.toInt(),
                        it.strIngredient,
                        100,
                        0.0
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
                // Return a success resource
                Resource.Success(Unit)
            }
    }
    override fun getAllIngredients(): Observable<List<Ingredient>> {

            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Ingredient(it.idIngredient,it.strIngredient,it.value100g,it.caloricValueIn100g)
                    }
                }

    }
    override fun getAllIngredientsAscending(): Observable<List<Ingredient>> {
        return localDataSource
            .getAllAscending()
            .map {
                it.map {
                    Ingredient(it.idIngredient,it.strIngredient,it.value100g,it.caloricValueIn100g)
                }
            }
    }
    override fun getAllIngredientsDescending(): Observable<List<Ingredient>> {
        return localDataSource
            .getAllDescending()
            .map {
                it.map {
                    Ingredient(it.idIngredient,it.strIngredient,it.value100g,it.caloricValueIn100g)
                }
            }
    }
    override fun getAllByName(name: String): Observable<List<Ingredient>> {
        return localDataSource
            .getByName(name)
            .map {
                it.map {
                    Ingredient(it.idIngredient,it.strIngredient,it.value100g,it.caloricValueIn100g)
                }
            }
    }


}