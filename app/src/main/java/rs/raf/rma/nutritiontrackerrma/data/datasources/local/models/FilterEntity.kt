package rs.raf.rma.nutritiontrackerrma.data.datasources.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "itemFilters")
data class FilterEntity(
    @PrimaryKey
    var name: String,
    )