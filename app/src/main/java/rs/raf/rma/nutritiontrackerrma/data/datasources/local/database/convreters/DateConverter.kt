package rs.raf.rma.nutritiontrackerrma.data.datasources.local.database.convreters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun fromTimeStamp(value : Long) : Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimeStamp(date : Date) : Long{
        return date.time
    }


}