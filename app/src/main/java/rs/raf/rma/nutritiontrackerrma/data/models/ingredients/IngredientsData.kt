package rs.raf.rma.nutritiontrackerrma.data.models.ingredients

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientData(@Json(name = "idIngredient") val idIngredient: String,
                        @Json(name = "strIngredient") val strIngredient: String)

@JsonClass(generateAdapter = true)
data class IngredientResponse(
    val meals: List<IngredientData>
)
