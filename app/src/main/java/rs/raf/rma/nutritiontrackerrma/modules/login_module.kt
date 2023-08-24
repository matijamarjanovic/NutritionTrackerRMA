package rs.raf.rma.nutritiontrackerrma.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.MealsDatabase
import rs.raf.rma.nutritiontrackerrma.data.datasources.remote.CategoryService
import rs.raf.rma.nutritiontrackerrma.data.models.user.User
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.categoryrepositories.CategoryRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.data.repositories.user.UserRepository
import rs.raf.rma.nutritiontrackerrma.data.repositories.user.UserRepositoryImpl
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.LoginViewModel

val loginModule = module {

    viewModel { LoginViewModel (userRepository = get()) }

    single <UserRepository> { UserRepositoryImpl( localDataSource = get()) }
    single { get<MealsDatabase>().getIngredientsDao() }
    single { get<MealsDatabase>().getUsersDao() }

}