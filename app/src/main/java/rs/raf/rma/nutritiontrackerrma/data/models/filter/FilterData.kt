package rs.raf.rma.nutritiontrackerrma.data.models.filter

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterDataArea(@Json(name = "strArea") val name: String)
@JsonClass(generateAdapter = true)
data class FilterDataCat(@Json(name = "strCategory") val name: String)
@JsonClass(generateAdapter = true)
data class FilterDataIng(@Json(name = "strIngredient") val name: String)

@JsonClass(generateAdapter = true)
data class FilterResponseArea(
    val filterItems: List<FilterDataArea>
)
@JsonClass(generateAdapter = true)
data class FilterResponseCat(
    val filterItems: List<FilterDataCat>
)
@JsonClass(generateAdapter = true)
data class FilterResponseIng(
    val filterItems: List<FilterDataIng>
)

