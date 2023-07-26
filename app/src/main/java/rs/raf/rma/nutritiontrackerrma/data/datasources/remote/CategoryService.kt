package rs.raf.rma.nutritiontrackerrma.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.CategoryData

interface CategoryService {

    @GET("categories.php")
    fun getAll(@Query("limit") limit: Int = 1000) : Observable<List<CategoryData>>

}