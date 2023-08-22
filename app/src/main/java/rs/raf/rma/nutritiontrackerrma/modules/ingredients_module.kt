package rs.raf.rma.nutritiontrackerrma.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.FilterService
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.IngredientsService
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.data.repositories.filter.FilterRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.filter.FilterRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.data.repositories.ingredients.IngredientsRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.ingredients.IngredientsRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.FilterViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.IngredientsViewModel


val ingredientsModule = module {

    viewModel { IngredientsViewModel(ingredientsRepository = get()) }

    single <IngredientsRepository> { IngredientsRepositoryImpl(remoteDataSource = get(), localDataSource = get()) }

    single { get<MealsDatabase>().getIngredientsDao() }

    single<IngredientsService > { create(retrofit = get(qualifier = named("mealsRetrofit")))}

}