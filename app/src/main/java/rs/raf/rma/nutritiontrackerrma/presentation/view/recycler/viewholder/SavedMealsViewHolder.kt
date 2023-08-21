package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.data.models.meals.SavedMeal
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMeal
import rs.raf.rma.nutritiontrackerrma.databinding.SavedMealItemBinding

class SavedMealsViewHolder (private val itemBinding: SavedMealItemBinding, private val context : Context) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: SavedMeal, onButtonClick: (String) -> Unit,) {
        itemBinding.titleTv.text = meal.strMeal
        itemBinding.calorieTe.text = meal.calories.toString()

        itemBinding.root.setOnClickListener{
            onButtonClick(meal.strMeal.toString())
        }

        Glide
            .with(context)
            .load(meal.strMealThumb)
            .into(itemBinding.imageview)
    }

}