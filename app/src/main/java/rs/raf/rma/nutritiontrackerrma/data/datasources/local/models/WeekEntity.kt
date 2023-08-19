package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import java.util.Date

@Entity(tableName = "week")
data class WeekEntity(
    val day: String,
    val calories: Double ,
    val mealId : Int,
    val date:Date
)