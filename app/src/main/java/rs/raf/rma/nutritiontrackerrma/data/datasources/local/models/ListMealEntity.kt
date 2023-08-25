package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class ListMealEntity(
    @PrimaryKey
    var idMeal: Int,
    var strMeal: String,
    var strMealThumb : String,
    var calories: Double,
)