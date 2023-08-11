package rs.raf.rma.nutritiontrackerrma.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryData(@Json(name = "idCategory") val id: String,
                        @Json(name = "strCategory") val name: String,
                        @Json(name = "strCategoryThumb") val thumbLink: String,
                        @Json(name = "strCategoryDescription") val desc: String)

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val categories: List<CategoryData>
)

/*
fun CategoryData.toCategory() : Category{
    return Category(name, thumbLink, desc)
}
*/
