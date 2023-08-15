package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal

class FilterDiffCallback : DiffUtil.ItemCallback<Filter>() {

    override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
        return oldItem.name == newItem.name
    }

}