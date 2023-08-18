package rs.raf.rma.nutritiontrackerrma.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.MealsService
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.meal.ListMealRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel

val savedMealsModule = module {

    viewModel { MealsViewModel(listMealRepository = get()) }

    single<ListMealRepository> { ListMealRepositoryImpl(localDataSource = get(), remoteDataSource = get(),remoteDataSourceCalories =get()) }

    single { get<MealsDatabase>().getSavedMealDao()  }

    single<MealsService> {  create(retrofit = get(qualifier = named("mealsRetrofit"))) }
    //single<CaloriesService> {  create(retrofit = get(qualifier = named("caloriesRetrofit"))) }

}
