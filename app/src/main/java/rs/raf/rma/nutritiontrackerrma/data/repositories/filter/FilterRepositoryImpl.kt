package rs.raf.rma.nutritiontrackerrma.data.repositories.filter

import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.FilterDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.FilterService
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import timber.log.Timber


class FilterRepositoryImpl(

    private val localDataSource: FilterDao,
    private val remoteDataSource: FilterService

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

    override fun getAllAreas(asc: Boolean): Observable<List<Filter>> {

        if (asc){
            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }
        }else{
            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }
        }
    }

    override fun getAllCategories(asc: Boolean): Observable<List<Filter>> {

        if (asc){
            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }
        }else{
            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }
        }
    }

    override fun getAllIngredients(asc: Boolean): Observable<List<Filter>> {


        if (asc){
            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
                    }
                }
        }else{
            return localDataSource
                .getAll()
                .map {
                    it.map {
                        Filter(it.name)
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