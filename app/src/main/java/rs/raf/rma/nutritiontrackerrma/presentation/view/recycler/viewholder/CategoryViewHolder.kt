package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.databinding.CategoryItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.CategoryContract
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.CategoryViewModel

class CategoryViewHolder(private val itemBinding: CategoryItemBinding, private val context : Context) : RecyclerView.ViewHolder(itemBinding.root) {


    fun bind(cat: Category, onButtonClick: (String) -> Unit){
        itemBinding.titleTv.text = cat.name

        itemBinding.itemOptionsButton.setOnClickListener{
            onButtonClick(cat.desc)
        }

        Glide
            .with(context)
            .load(cat.thumbLink)
            .into(itemBinding.itemImage)


    }
}