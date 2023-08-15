package rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListMealResponse(
    val listMeals: List<ListMealData>
)
@JsonClass(generateAdapter = true)
data class ListMealData(
    val idMeal: Int,
    val strMeal: String,
    val strMealTumb : String,
)