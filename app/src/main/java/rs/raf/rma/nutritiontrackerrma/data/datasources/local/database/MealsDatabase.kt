package rs.raf.rma.nutritiontrackerrma.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.CategoryDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.FilterDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.IngredientsDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListSingleMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.SavedMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.UserDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.DateConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.ListConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.IngredientEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListSingleMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.UserEntity

@Database(
    entities = [CategoryEntity::class, ListMealEntity::class,
        FilterEntity::class, SavedMealEntity::class,
        UserEntity::class,IngredientEntity::class,
        ListSingleMealEntity::class],
    version = 18,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun getCategoryDao() : CategoryDao
    abstract fun getListMealDao() : ListMealDao
    abstract fun getFilterDao() : FilterDao
    abstract fun getSavedMealDao(): SavedMealDao
    abstract fun getUsersDao(): UserDao
    abstract fun getIngredientsDao(): IngredientsDao
    abstract fun getListSingleMealDao(): ListSingleMealDao

}