package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.SingleMealItemBinding


class SingleMealViewHolder(private val itemBinding: SingleMealItemBinding, private val context: Context) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: Meal, onButtonClick: (String) -> Unit) {

        val ingredients = ArrayList<String>().apply {
            meal.strIngredient1?.let { add(it) }
            meal.strIngredient2?.let { add(it) }
            meal.strIngredient3?.let { add(it) }
            meal.strIngredient4?.let { add(it) }
            meal.strIngredient5?.let { add(it) }
            meal.strIngredient6?.let { add(it) }
            meal.strIngredient7?.let { add(it) }
            meal.strIngredient8?.let { add(it) }
            meal.strIngredient9?.let { add(it) }
            meal.strIngredient10?.let { add(it) }
            meal.strIngredient11?.let { add(it) }
            meal.strIngredient12?.let { add(it) }
            meal.strIngredient13?.let { add(it) }
            meal.strIngredient14?.let { add(it) }
            meal.strIngredient15?.let { add(it) }
            meal.strIngredient16?.let { add(it) }
            meal.strIngredient17?.let { add(it) }
            meal.strIngredient18?.let { add(it) }
            meal.strIngredient19?.let { add(it) }
            meal.strIngredient20?.let { add(it) }
        }

        itemBinding.mealNameTextView.text = meal.strMeal
        itemBinding.videoLinkTextView.text = meal.strYoutube
        itemBinding.areaTextView.text = meal.strArea
        itemBinding.kcalTextView.text = meal.calories.toString()

        itemBinding.btn.setOnClickListener{
            onButtonClick(meal.strInstructions)
        }

        for (s : String in ingredients){
            if(s != "")
                itemBinding.ingredientsTextView.text = s  + ", " + itemBinding.ingredientsTextView.text.toString()
        }

        itemBinding.addBtn.setOnClickListener{
            onButtonClick("add")
        }

        itemBinding.root.setOnClickListener{
        }

        Glide
            .with(context)
            .load(meal.strMealThumb)
            .into(itemBinding.mealImageView)
    }

}