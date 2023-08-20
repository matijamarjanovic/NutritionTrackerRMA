package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.AddMealItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.SingleMealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.AddMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.SingleMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.AddMealViewHolder
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.SingleMealViewHolder
import java.util.Date

class AddMealAdapter (private val onButtonClick: (Meal, String, Date) -> Unit) : ListAdapter<Meal, AddMealViewHolder>(
    AddMealDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMealViewHolder {
        val itemBinding = AddMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddMealViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: AddMealViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }

}