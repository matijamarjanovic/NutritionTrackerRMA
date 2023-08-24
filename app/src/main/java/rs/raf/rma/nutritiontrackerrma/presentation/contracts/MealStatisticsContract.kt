package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import com.google.common.graph.Graph
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.AddListMealState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.GraphState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealPageState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.MealsState
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.SavedMealsState
import java.util.Date

interface MealStatisticsContract {
    interface ViewModel {

        val graphState :LiveData<GraphState>
        fun getMealsIn7DaysByNumbers(user:String,days:List<String>)
        fun getMealsIn7DaysByCalories(user:String,days:List<String>)

    }
}