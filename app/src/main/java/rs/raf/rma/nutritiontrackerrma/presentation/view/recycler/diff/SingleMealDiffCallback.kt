package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

class SingleMealDiffCallback : DiffUtil.ItemCallback<Meal>(){

    override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
        return oldItem.strMeal == newItem.strMeal &&
                oldItem.strMealThumb == newItem.strMealThumb
    }
}