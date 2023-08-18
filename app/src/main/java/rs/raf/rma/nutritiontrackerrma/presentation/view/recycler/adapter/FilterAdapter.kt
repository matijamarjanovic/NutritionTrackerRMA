package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterDataArea
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterDataCat
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterDataIng
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutFilterItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.FilterDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.MealsDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.FilterViewHolder
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.MealsViewHolder

class FilterAdapter(private val onButtonClick: (String) -> Unit) : ListAdapter<Filter, FilterViewHolder>(FilterDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val itemBinding = LayoutFilterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FilterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }
    

}