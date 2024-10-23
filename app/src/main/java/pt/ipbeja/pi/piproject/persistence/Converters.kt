package pt.ipbeja.pi.piproject.persistence

import androidx.room.TypeConverter
import java.util.Date

/**
 * Converts Date into int timestamps back and forth
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}