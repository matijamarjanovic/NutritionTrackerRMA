package rs.raf.rma.nutritiontrackerrma.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.CategoryDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.FilterDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.ListMealDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.DateConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.FilterEntity
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.ListMealEntity

@Database(
    entities = [CategoryEntity::class, ListMealEntity::class, FilterEntity::class],
    version = 8,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun getCategoryDao() : CategoryDao
    abstract fun getListMealDao() : ListMealDao
    abstract fun getFilterDao() : FilterDao

}