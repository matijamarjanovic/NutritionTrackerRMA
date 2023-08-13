package rs.raf.rma.nutritiontrackerrma.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.rma.nutritiontrackerrma.data.models.Category
import rs.raf.rma.nutritiontrackerrma.data.models.CategoryData
import rs.raf.rma.nutritiontrackerrma.data.models.CategoryResponse

interface CategoryService {

    @GET("categories.php")
    fun getAll() : Observable<CategoryResponse>

    //@Query("limit") limit: Int = 50
}