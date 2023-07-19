package rs.raf.rma.nutritiontrackerrma.models

import android.hardware.Camera.Area

class Meal(val id: Int, val name: String, val category: Category, val area: Area,
           val drinkAlt: String, val instructions: String, val thumbnailLink: String,
            val tags: ArrayList<String>, val ytLink: String, val ingridients: Map<Ingredient, String>) {


}