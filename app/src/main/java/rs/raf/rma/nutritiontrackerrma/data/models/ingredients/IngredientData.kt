package rs.raf.rma.nutritiontrackerrma.data.models.ingredients

data class IngredientData(var id: Int,
                 var name: String,
                 var type: String,
                 var desc: String)

fun IngredientData.toIngredient() : Ingredient {
    return Ingredient(name, type, desc)
}
