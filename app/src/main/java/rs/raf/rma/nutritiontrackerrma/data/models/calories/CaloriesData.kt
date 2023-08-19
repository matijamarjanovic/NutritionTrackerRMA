package rs.raf.rma.nutritiontrackerrma.data.models.calories

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import rs.raf.rma.nutritiontrackerrma.data.models.categories.CategoryData
import rs.raf.rma.nutritiontrackerrma.data.models.categories.CategoryResponse

@JsonClass(generateAdapter = true)
data class CaloriesResponse(  val name: String,
                              val calories: Double,
                             )

//@JsonClass(generateAdapter = true)
//data class CategoryResponse(
//    val calories: List<CategoryResponse>
//)
