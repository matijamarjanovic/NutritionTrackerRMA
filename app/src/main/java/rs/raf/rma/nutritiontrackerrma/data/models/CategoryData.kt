package rs.raf.rma.nutritiontrackerrma.data.models

data class CategoryData(var id: Int,
                        var name: String,
                        var thumbLink: String,
                        var desc: String,
                        var image : Int )

fun CategoryData.toCategory() : Category{
    return Category(name, thumbLink, desc, image)
}