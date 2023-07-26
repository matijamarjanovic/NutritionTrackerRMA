package rs.raf.rma.nutritiontrackerrma.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.repositories.CategoryRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.CategoryRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel

val categoryModule = module {

    viewModel { CategoryViewModel (categoryRepository = get()) }

    single <CategoryRepository> { CategoryRepositoryImpl(remoteDataSource = get(), localDataSource = get()) }

    single { get<MealsDatabase>().getCategoryDao() }

    single<CategoryService> { create(retrofit = get())}
}