package rs.raf.rma.nutritiontrackerrma.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.CategoryDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.Resource
import timber.log.Timber

class CategoryRepositoryImpl (
    private val localDataSource: CategoryDao,
    private val remoteDataSource: CategoryService
) : CategoryRepository {

    override fun fetchAll(): Observable<Resource<Unit>> {

        return remoteDataSource
            .getAll()
            .map { response ->
                val categories = response.categories

                val entities = categories.map {
                    CategoryEntity(
                        0,
                        it.name,
                        it.thumbLink,
                        it.desc
                    )
                }

                Timber.e(entities[1].toString())
                //TODO problem sa fetchom
                localDataSource.deleteAndInsertAll(entities)

                // Return a success resource
                Resource.Success(Unit)
            }

        /*        return remoteDataSource
            .getAll()
            .doOnNext {
                Timber.e("Upis u bazu")
                val entities = it.map {
                    CategoryEntity(
                        0,
                        it.name,
                        it.thumbLink,
                        it.desc
                    )
                }

                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }*/
    }

    override fun getAll(): Observable<List<Category>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Category(it.name, it.thumb, it.desc)
                }
            }
    }

    override fun insert(cat: Category): Completable {
        val categoryEntity = CategoryEntity(0, cat.name, cat.thumbLink, cat.desc)
        return localDataSource
            .insertCategory(categoryEntity)
    }
}