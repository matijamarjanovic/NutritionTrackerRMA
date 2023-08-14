package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    var id: Int,
    var name: String,
    var thumb: String,
    var desc: String
    )