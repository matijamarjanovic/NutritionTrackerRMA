package rs.raf.rma.nutritiontrackerrma.data.models.meals.singleMeals

data class SingleMeal(

    val idMeal: Int,
    val strMeal: String,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealTumb : String,
    val strTags: String,
    val strYoutube: String,

    val listIngredient :ArrayList<String>,
    val listMeasure :ArrayList<String>

//
//    "idMeal": "52772",
//"strMeal": "Teriyaki Chicken Casserole",
//"strDrinkAlternate": null,
//"strCategory": "Chicken",
//"strArea": "Japanese",
//"strInstructions": "Preheat oven to 350° F. Spray a 9x13-inch baking pan with non-stick spray.\r\nCombine soy sauce, ½ cup water, brown sugar, ginger and garlic in a small saucepan and cover. Bring to a boil over medium heat. Remove lid and cook for one minute once boiling.\r\nMeanwhile, stir together the corn starch and 2 tablespoons of water in a separate dish until smooth. Once sauce is boiling, add mixture to the saucepan and stir to combine. Cook until the sauce starts to thicken then remove from heat.\r\nPlace the chicken breasts in the prepared pan. Pour one cup of the sauce over top of chicken. Place chicken in oven and bake 35 minutes or until cooked through. Remove from oven and shred chicken in the dish using two forks.\r\n*Meanwhile, steam or cook the vegetables according to package directions.\r\nAdd the cooked vegetables and rice to the casserole dish with the chicken. Add most of the remaining sauce, reserving a bit to drizzle over the top when serving. Gently toss everything together in the casserole dish until combined. Return to oven and cook 15 minutes. Remove from oven and let stand 5 minutes before serving. Drizzle each serving with remaining sauce. Enjoy!",
//"strMealThumb": "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
//"strTags": "Meat,Casserole",
//"strYoutube": "https://www.youtube.com/watch?v=4aZr5hZXP_s",
//
    )