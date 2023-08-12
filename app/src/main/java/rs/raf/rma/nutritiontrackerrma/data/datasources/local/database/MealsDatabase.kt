package rs.raf.rma.nutritiontrackerrma.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.dao.CategoryDao
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters.DateConverter
import rs.raf.rma.nutritiontrackerrma.data.datasources.local.models.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun getCategoryDao() : CategoryDao
}