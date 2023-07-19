package rs.raf.rma.nutritiontrackerrma.fragments.categoriesRecycler.differ

import androidx.recyclerview.widget.DiffUtil
import rs.raf.rma.nutritiontrackerrma.models.Category

class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {


    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.name == newItem.name && oldItem.desc == newItem.desc
    }
}