package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.R
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.databinding.CategoryItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.view.fragments.HomepageFragment
import rs.raf.rma.nutritiontrackerrma.presentation.view.fragments.ListMealFragment
import rs.raf.rma.nutritiontrackerrma.presentation.view.fragments.MealPageFragment

class CategoryViewHolder(private val itemBinding: CategoryItemBinding, private val context : Context) : RecyclerView.ViewHolder(itemBinding.root) {


    fun bind(cat: Category, onButtonClick: (String) -> Unit){
        itemBinding.titleTv.text = cat.name

        itemBinding.itemOptionsButton.setOnClickListener{
            onButtonClick(cat.desc)
        }

        itemBinding.root.setOnClickListener{
            onButtonClick(cat.name)
        }

        Glide
            .with(context)
            .load(cat.thumbLink)
            .into(itemBinding.itemImage)

    }
}