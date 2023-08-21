package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import rs.raf.rma.nutritiontrackerrma.data.models.meals.singleMeals.SingleMealResponse

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    var username: String,
    var password: String
)