package rs.raf.rma.nutritiontrackerrma.data.models.meals

import android.hardware.Camera
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category

data class MealData(var id: Int,
                    var name: String,
                    var category: Category,
                    var area: Camera.Area,
                    var drinkAlt: String,
                    var instructions: String,
                    var thumbnailLink: String,
                    var tags: ArrayList<String>,
                    var ytLink: String,
                    )
