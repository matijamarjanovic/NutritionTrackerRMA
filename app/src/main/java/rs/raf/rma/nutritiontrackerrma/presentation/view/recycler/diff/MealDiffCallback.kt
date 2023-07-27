package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.Meal

class MealDiffCallback : DiffUtil.ItemCallback<Meal>() {

    override fun areItemsTheSame(
        oldItem: Meal,
        newItem: Meal
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.ingridients == newItem.ingridients
    }

    override fun areContentsTheSame(
        oldItem: Meal,
        newItem: Meal
    ): Boolean {
        return oldItem.ingridients == newItem.ingridients
    }
}