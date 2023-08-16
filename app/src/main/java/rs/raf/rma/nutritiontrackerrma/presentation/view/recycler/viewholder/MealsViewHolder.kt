package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding


class MealsViewHolder(private val itemBinding: LayoutItemMealBinding, private val context : Context) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: ListMeal) {
        itemBinding.titleTv.text = meal.strMeal

        Glide
            .with(context)
            .load(meal.strMealThumb)
            .into(itemBinding.imageview)
    }

}