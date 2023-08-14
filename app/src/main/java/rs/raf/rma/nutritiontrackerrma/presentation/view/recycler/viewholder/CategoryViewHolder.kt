package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.databinding.CategoryItemBinding

class CategoryViewHolder(private val itemBinding: CategoryItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(cat : Category){
        itemBinding.titleTv.text = cat.name
    }
}