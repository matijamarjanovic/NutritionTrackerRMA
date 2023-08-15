package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemFilters")
data class FilterEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    )