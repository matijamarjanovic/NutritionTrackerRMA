package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.CategoryItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.MealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.MealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.CategoryViewHolder
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.MealViewHolder

class MealAdapter : ListAdapter<Meal, MealViewHolder>(MealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemBinding = MealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}