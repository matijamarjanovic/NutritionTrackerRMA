package rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters

import androidx.room.TypeConverter
import java.util.Date

class ListConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<Double> {
        val list = ArrayList<Double>()
        val items = value.split(",").map { it.toDouble() }
        list.addAll(items)
        return list
    }

    @TypeConverter
    fun toString(list: ArrayList<Double>): String {
        return list.joinToString(",")
    }

}