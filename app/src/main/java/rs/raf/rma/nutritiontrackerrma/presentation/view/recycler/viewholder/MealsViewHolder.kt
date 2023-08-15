package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding

class MealsViewHolder(private val itemBinding: LayoutItemMealBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: ListMeal) {
        itemBinding.titleTv.text = meal.strMeal
    }

}