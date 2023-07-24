package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.CategoryItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.MealItemBinding

class MealViewHolder(private val itemBinding: MealItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: Meal){
        itemBinding.titleTv.text = meal.name
    }
}