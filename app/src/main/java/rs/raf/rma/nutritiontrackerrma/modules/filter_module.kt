package rs.raf.rma.nutritiontrackerrma.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.FilterService
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.data.repositories.filter.FilterRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.filter.FilterRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.FilterViewModel


val filterModule = module {

    viewModel { FilterViewModel(filterRepository = get()) }

    single <FilterRepository> { FilterRepositoryImpl(remoteDataSource = get(), remoteDataSourceCalories = get(), localDataSource = get(),localDataSourceIng =get()) }

    single { get<MealsDatabase>().getFilterDao() }

    single<FilterService> { create(retrofit = get(qualifier = named("mealsRetrofit")))}

}