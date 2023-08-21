package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.databinding.SingleMealItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.SingleSavedMealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.SingleMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.SingleSavedMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.SingleMealViewHolder
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.SingleSavedMealViewHolder

class SingleSavedMealAdapter (private val onButtonClick: (String) -> Unit) : ListAdapter<SavedMeal, SingleSavedMealViewHolder>(
    SingleSavedMealDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleSavedMealViewHolder {
        val itemBinding = SingleSavedMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SingleSavedMealViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: SingleSavedMealViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }

}