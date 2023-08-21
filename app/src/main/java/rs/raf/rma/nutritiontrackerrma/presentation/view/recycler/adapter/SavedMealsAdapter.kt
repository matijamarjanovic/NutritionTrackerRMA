package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.SavedMealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.MealsDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.SavedMealsDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.MealsViewHolder
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.SavedMealsViewHolder

class SavedMealsAdapter(private val onButtonClick: (String) -> Unit) : ListAdapter<SavedMeal, SavedMealsViewHolder>(
    SavedMealsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedMealsViewHolder {
        val itemBinding = SavedMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedMealsViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: SavedMealsViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }

}