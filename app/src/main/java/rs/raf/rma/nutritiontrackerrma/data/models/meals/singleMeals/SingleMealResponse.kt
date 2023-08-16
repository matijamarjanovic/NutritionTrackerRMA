package rs.raf.rma.nutritiontrackerrma.data.models.meals.singleMeals

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SingleMealResponse(
    val meals: List<SingleMealData>
)
@JsonClass(generateAdapter = true)
data class SingleMealData(
    val idMeal: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealTumb : String,
    val strTags: String,
    val strYoutube: String,
)