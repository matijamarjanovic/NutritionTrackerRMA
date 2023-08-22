package rs.raf.rma.nutritiontrackerrma.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import rs.raf.rma.nutritiontrackerrma.data.repositories.mealStatistics.MealStatisticsRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.mealStatistics.MealStatisticsRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealStatisticsViewModel

val mealStaisticsModule = module {
    viewModel { MealStatisticsViewModel(mealStatisticsRepository = get()) }

    single<MealStatisticsRepository> { MealStatisticsRepositoryImpl (localDataSource = get()) }

    single { get<MealsDatabase>().getSavedMealDao()  }
}

