package rs.raf.rma.nutritiontrackerrma.data.models

import android.hardware.Camera.Area

class Meal(var id: Int, var name: String, var category: Category, var area: Area,
           var drinkAlt: String, var instructions: String, var thumbnailLink: String,
           var tags: ArrayList<String>, var ytLink: String, var ingridients: Map<Ingredient, String>) {


}