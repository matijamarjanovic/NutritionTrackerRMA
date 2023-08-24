package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.SingleMealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.IngredientsContract
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.SingleMealDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.SingleMealViewHolder

class SingleMealAdapter(private val onButtonClick: (String) -> Unit) : ListAdapter<Meal, SingleMealViewHolder>(SingleMealDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleMealViewHolder {
        val itemBinding = SingleMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SingleMealViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: SingleMealViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)
    }

}