package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.databinding.AddMealItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.UpdateMealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.AddMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.UpdateMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.AddMealViewHolder
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.UpdateMealViewHolder
import java.util.*

class UpdateMealAdapter (private val onButtonClick: (SavedMeal, String, Date) -> Unit) : ListAdapter<SavedMeal, UpdateMealViewHolder>(
    UpdateMealDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateMealViewHolder {
        val itemBinding = UpdateMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpdateMealViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: UpdateMealViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }

}