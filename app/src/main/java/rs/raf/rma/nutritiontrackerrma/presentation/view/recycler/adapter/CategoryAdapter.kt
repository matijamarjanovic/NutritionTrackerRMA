package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ListAdapter
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.databinding.CategoryItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder.CategoryViewHolder

class CategoryAdapter(private val onButtonClick: (String) -> Unit) : ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onButtonClick)

    }
}