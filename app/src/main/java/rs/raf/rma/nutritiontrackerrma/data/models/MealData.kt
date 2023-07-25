package rs.raf.rma.nutritiontrackerrma.data.models

import android.hardware.Camera

data class MealData(var id: Int,
                    var name: String,
                    var category: Category,
                    var area: Camera.Area,
                    var drinkAlt: String,
                    var instructions: String,
                    var thumbnailLink: String,
                    var tags: ArrayList<String>,
                    var ytLink: String,
                    var ingridients: Map<Ingredient, String>)

fun MealData.toMeal() : Meal {
    return Meal(name, category, area, drinkAlt, instructions, thumbnailLink, tags, ytLink, ingridients)
}