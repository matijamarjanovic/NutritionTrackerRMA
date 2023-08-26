package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.*
import java.util.Date

interface MealsContract {

    interface ViewModel {

        val mealsState: LiveData<MealsState>
        val mealsState2: LiveData<MealPageState>
        val mealsState3: LiveData<MealPageState>
        val savedMealState: LiveData<SavedMealsState>
        val savedMealState2: LiveData<SavedMealPageState>
        val caloriesState: LiveData<CaloriesState>


        val addDone: LiveData<AddListMealState>

        fun fetchAllMealsByArea(area:String)
        fun fetchAllMealsByCategory(category: String)
        fun fetchAllMealsByIngridient(ingridient: String)
        fun getSingleMeal(mealId : String)
        fun getAllSingleMeals(mealList: ArrayList<ListMeal>)
        fun getAllMeals()
        fun getAllMealsSortedByCal(min:Int,max:Int)
        fun getMealByName(name: String)
        fun getAllSavedMeals(user:String)
        fun getSavedMealByName(name: String)
        fun addMeal(meal: Meal, whichMeal : String, date : Date)
        fun updateMeal(meal: SavedMeal, whichMeal : String, date : Date)
        fun getSingleSavedMeal(mealName: String)
        fun deleteMeal(id : String)
        fun addKcalToMeal(meals: ArrayList<Meal>)


    }

}