package rs.raf.rma.nutritiontrackerrma.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.rma.nutritiontrackerrma.data.models.categories.Category
import rs.raf.rma.nutritiontrackerrma.presentation.view.states.CategoryState

interface CategoryContract {

    interface ViewModel {
        val categoryState : LiveData<CategoryState>
        fun fetchAllCategories()
        fun getAllCategories()
        fun getAllCategoriesByPage(pageNumber:Int,itemNumber:Int)
        fun getCategoryByName(name: String)

    }
}