package rs.raf.rma.nutritiontrackerrma.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rma.nutritiontrackerrma.data.models.categories.CategoryResponse
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseArea
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseCat
import rs.raf.rma.nutritiontrackerrma.data.models.filter.FilterResponseIng
import rs.raf.rma.nutritiontrackerrma.data.models.meals.listMeals.ListMealResponse
import rs.raf.rma.nutritiontrackerrma.data.models.meals.singleMeals.SingleMealResponse

interface FilterService {
    @GET("list.php?c=list")
    fun getAllCategories(): Observable<FilterResponseCat>
    @GET("list.php?a=list")
    fun getAllAreas(): Observable<FilterResponseArea>
    @GET("list.php?i=list")
    fun getAllIngredients(): Observable<FilterResponseIng>
}