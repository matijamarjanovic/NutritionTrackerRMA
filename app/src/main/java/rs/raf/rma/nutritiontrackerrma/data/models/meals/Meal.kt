package rs.raf.rma.nutritiontrackerrma.data.models.meals

import android.hardware.Camera.Area
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category

class Meal(var name: String,
           var category: Category,
           var area: Area,
           var drinkAlt: String,
           var instructions: String,
           var thumbnailLink: String,
           var tags: ArrayList<String>,
           var ytLink: String,
)