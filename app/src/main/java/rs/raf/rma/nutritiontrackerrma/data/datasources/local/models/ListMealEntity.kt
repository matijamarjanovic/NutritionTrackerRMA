package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class ListMealEntity(
    @PrimaryKey
    val idMeal: Int,
    val strMeal: String,
    val strMealThumb : String,
    var calories: Double,
)