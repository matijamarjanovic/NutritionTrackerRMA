package rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListMealResponse(
    val meals: List<ListMealData>
)
@JsonClass(generateAdapter = true)
data class ListMealData(
    @Json(name = "idMeal") val idMeal: Int,
    @Json(name = "strMeal")val strMeal: String,
    @Json(name = "strMealThumb")val strMealThumb : String,
)