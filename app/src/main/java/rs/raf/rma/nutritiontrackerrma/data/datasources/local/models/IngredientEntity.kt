package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true)
    var idIngredient: Int,
    var strIngredient: String,
    var value100g:Int,
    var caloricValueIn100g:Double
    )