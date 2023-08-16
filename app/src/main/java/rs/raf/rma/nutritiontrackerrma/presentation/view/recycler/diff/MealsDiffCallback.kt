package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

class MealsDiffCallback : DiffUtil.ItemCallback<ListMeal>() {

    override fun areItemsTheSame(oldItem: ListMeal, newItem: ListMeal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: ListMeal, newItem: ListMeal): Boolean {
        return oldItem.strMeal == newItem.strMeal &&
                oldItem.strMealThumb == newItem.strMealThumb
    }

}