package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.rma.nutritiontrackerrma.data.models.filter.Filter
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutFilterItemBinding
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding

class FilterViewHolder(private val itemBinding: LayoutFilterItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: Filter, onButtonClick: (String) -> Unit) {
        itemBinding.titleTv.text = item.name

        itemBinding.root.setOnClickListener{
            onButtonClick(item.name)
        }
    }

}