package rs.raf.rma.nutritiontrackerrma.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.*
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.DateConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.SavedMealEntity

@Database(
    entities = [CategoryEntity::class, ListMealEntity::class, FilterEntity::class, SavedMealEntity::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MealsDatabase : RoomDatabase() {
    abstract fun getCategoryDao() : CategoryDao
    abstract fun getListMealDao() : ListMealDao
    abstract fun getFilterDao() : FilterDao
    abstract fun getSavedMealDao(): SavedMealDao

    abstract fun getUserDao(): UserDao

}