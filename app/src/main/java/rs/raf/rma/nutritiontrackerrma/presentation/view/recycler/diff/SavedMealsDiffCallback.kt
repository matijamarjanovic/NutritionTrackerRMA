package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

class SavedMealsDiffCallback : DiffUtil.ItemCallback<SavedMeal>() {

    override fun areItemsTheSame(oldItem: SavedMeal, newItem: SavedMeal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: SavedMeal, newItem: SavedMeal): Boolean {
        return oldItem.strMeal == newItem.strMeal &&
                oldItem.strMealThumb == newItem.strMealThumb
    }

}