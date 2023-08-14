package rs.raf.rma.nutritiontrackerrma.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import rs.raf.rma.nutritiontrackerrma.data.models.categories.CategoryResponse

interface CategoryService {

    @GET("categories.php")
    fun getAll() : Observable<CategoryResponse>

    //@Query("limit") limit: Int = 50
}