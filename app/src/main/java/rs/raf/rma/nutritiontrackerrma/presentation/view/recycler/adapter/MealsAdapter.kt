package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.MealsDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.MealsViewHolder

class MealsAdapter(private val onButtonClick: (String) -> Unit) : ListAdapter<ListMeal, MealsViewHolder>(MealsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val itemBinding = LayoutItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }

}