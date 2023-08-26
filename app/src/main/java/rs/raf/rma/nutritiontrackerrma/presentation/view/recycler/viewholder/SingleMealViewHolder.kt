package rs.raf.rma.nutritiontrackerrma.presentation.view.recycler.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.rma.nutritiontrackerrma.data.models.meals.Meal
import rs.raf.rma.nutritiontrackerrma.databinding.LayoutItemMealBinding
import rs.raf.rma.nutritiontrackerrma.databinding.SingleMealItemBinding
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.IngredientsContract
import rs.raf.rma.nutritiontrackerrma.presentation.contracts.MealsContract
import rs.raf.rma.nutritiontrackerrma.presentation.viewmodels.MealsViewModel


class SingleMealViewHolder(private val itemBinding: SingleMealItemBinding, private val context: Context) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: Meal, onButtonClick : (String) -> Unit) {

        itemBinding.mealNameTextView.text = meal.strMeal
        itemBinding.videoLinkTextView.text = meal.strYoutube
        itemBinding.areaTextView.text = meal.strArea
        itemBinding.kcalTextView.text = meal.calories.toString()

        itemBinding.btn.setOnClickListener{
            onButtonClick(meal.strInstructions)
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

        itemBinding.ingredient1.text = meal.strIngredient1
        itemBinding.ingredient2.text = meal.strIngredient2
        itemBinding.ingredient3.text = meal.strIngredient3
        itemBinding.ingredient4.text = meal.strIngredient4
        itemBinding.ingredient5.text = meal.strIngredient5
        itemBinding.ingredient6.text = meal.strIngredient6
        itemBinding.ingredient7.text = meal.strIngredient7
        itemBinding.ingredient8.text = meal.strIngredient8
        itemBinding.ingredient9.text = meal.strIngredient9
        itemBinding.ingredient10.text = meal.strIngredient10
        itemBinding.ingredient11.text = meal.strIngredient11
        itemBinding.ingredient12.text = meal.strIngredient12
        itemBinding.ingredient13.text = meal.strIngredient13
        itemBinding.ingredient14.text = meal.strIngredient14
        itemBinding.ingredient15.text = meal.strIngredient15
        itemBinding.ingredient16.text = meal.strIngredient16
        itemBinding.ingredient17.text = meal.strIngredient17
        itemBinding.ingredient18.text = meal.strIngredient18
        itemBinding.ingredient19.text = meal.strIngredient19
        itemBinding.ingredient20.text = meal.strIngredient20

        itemBinding.measure1.text = meal.strMeasure1
        itemBinding.measure2.text = meal.strMeasure2
        itemBinding.measure3.text = meal.strMeasure3
        itemBinding.measure4.text = meal.strMeasure4
        itemBinding.measure5.text = meal.strMeasure5
        itemBinding.measure6.text = meal.strMeasure6
        itemBinding.measure7.text = meal.strMeasure7
        itemBinding.measure8.text = meal.strMeasure8
        itemBinding.measure9.text = meal.strMeasure9
        itemBinding.measure10.text = meal.strMeasure10
        itemBinding.measure11.text = meal.strMeasure11
        itemBinding.measure12.text = meal.strMeasure12
        itemBinding.measure13.text = meal.strMeasure13
        itemBinding.measure14.text = meal.strMeasure14
        itemBinding.measure15.text = meal.strMeasure15
        itemBinding.measure16.text = meal.strMeasure16
        itemBinding.measure17.text = meal.strMeasure17
        itemBinding.measure18.text = meal.strMeasure18
        itemBinding.measure19.text = meal.strMeasure19
        itemBinding.measure20.text = meal.strMeasure20


        var list =meal.caloriesList
        if (list != null) {

            for ((index, item) in list.withIndex()) {
                if(index==0){itemBinding.kcal1.text = item.toString()  }
                else if(index==1){ itemBinding.kcal2.text = item.toString() }
                else if(index==2){itemBinding.kcal3.text = item.toString()}
                else if(index==3){itemBinding.kcal4.text = item.toString()}
                else if(index==4){itemBinding.kcal5.text = item.toString()}
                else if(index==5){itemBinding.kcal6.text = item.toString()}
                else if(index==6){itemBinding.kcal7.text = item.toString()}
                else if(index==7){itemBinding.kcal8.text = item.toString()}
                else if(index==8){itemBinding.kcal9.text = item.toString()}
                else if(index==9){itemBinding.kcal10.text = item.toString()}
                else if(index==10){itemBinding.kcal11.text = item.toString()}
                else if(index==11){itemBinding.kcal12.text = item.toString()}
                else if(index==12){itemBinding.kcal13.text = item.toString()}
                else if(index==13){itemBinding.kcal14.text = item.toString()}
                else if(index==14){itemBinding.kcal15.text = item.toString()}
                else if(index==15){itemBinding.kcal16.text = item.toString()}
                else if(index==16){itemBinding.kcal17.text = item.toString()}
                else if(index==17){itemBinding.kcal18.text = item.toString()}
                else if(index==18){itemBinding.kcal19.text = item.toString()}
                else if(index==19){itemBinding.kcal20.text = item.toString()}

                //println("Position $index: $item") // Replace with the action you want to perform
            }
        }


//        itemBinding.kcal2.text = "0.0"
//        itemBinding.kcal3.text = "0.0"
//        itemBinding.kcal4.text = "0.0"
//        itemBinding.kcal5.text = "0.0"
//        itemBinding.kcal6.text = "0.0"
//        itemBinding.kcal7.text = "0.0"
//        itemBinding.kcal8.text = "0.0"
//        itemBinding.kcal9.text = "0.0"
//        itemBinding.kcal10.text = "0.0"
//        itemBinding.kcal11.text = "0.0"
//        itemBinding.kcal12.text = "0.0"
//        itemBinding.kcal13.text = "0.0"
//        itemBinding.kcal14.text = "0.0"
//        itemBinding.kcal15.text = "0.0"
//        itemBinding.kcal16.text = "0.0"
//        itemBinding.kcal17.text = "0.0"
//        itemBinding.kcal18.text = "0.0"
//        itemBinding.kcal19.text = "0.0"
//        itemBinding.kcal20.text = "0.0"

        if (meal.strIngredient1 == ""){
            itemBinding.im1.visibility = View.GONE
        }
        if (meal.strIngredient2 == ""){
            itemBinding.im2.visibility = View.GONE
        }
        if (meal.strIngredient3 == ""){
            itemBinding.im3.visibility = View.GONE
        }
        if (meal.strIngredient4 == ""){
            itemBinding.im4.visibility = View.GONE
        }
        if (meal.strIngredient5 == ""){
            itemBinding.im5.visibility = View.GONE
        }
        if (meal.strIngredient6 == ""){
            itemBinding.im6.visibility = View.GONE
        }
        if (meal.strIngredient7 == ""){
            itemBinding.im7.visibility = View.GONE
        }
        if (meal.strIngredient8 == ""){
            itemBinding.im8.visibility = View.GONE
        }
        if (meal.strIngredient9 == ""){
            itemBinding.im9.visibility = View.GONE
        }
        if (meal.strIngredient10 == ""){
            itemBinding.im10.visibility = View.GONE
        }
        if (meal.strIngredient11 == ""){
            itemBinding.im11.visibility = View.GONE
        }
        if (meal.strIngredient12 == ""){
            itemBinding.im12.visibility = View.GONE
        }
        if (meal.strIngredient13 == ""){
            itemBinding.im13.visibility = View.GONE
        }
        if (meal.strIngredient14 == ""){
            itemBinding.im14.visibility = View.GONE
        }
        if (meal.strIngredient15 == ""){
            itemBinding.im15.visibility = View.GONE
        }
        if (meal.strIngredient16 == ""){
            itemBinding.im16.visibility = View.GONE
        }
        if (meal.strIngredient17 == ""){
            itemBinding.im17.visibility = View.GONE
        }
        if (meal.strIngredient18 == ""){
            itemBinding.im18.visibility = View.GONE
        }
        if (meal.strIngredient19 == ""){
            itemBinding.im19.visibility = View.GONE
        }
        if (meal.strIngredient20 == ""){
            itemBinding.im20.visibility = View.GONE
        }

    }

}