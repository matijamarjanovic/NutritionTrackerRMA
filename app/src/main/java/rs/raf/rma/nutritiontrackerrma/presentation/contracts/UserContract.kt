package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.UserEntity
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.data.models.user.User
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.FilterState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.user.AddUserState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.user.UserState

interface UserContract {

    interface ViewModel {

        val userState: LiveData<UserState>
        val addDone: LiveData<AddUserState>
        fun addUser(user: User)
        fun checkUser(username:String):Int
        fun getUser(username : String):User
    }

}