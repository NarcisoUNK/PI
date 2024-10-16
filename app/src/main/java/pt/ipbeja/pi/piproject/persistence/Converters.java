package pt.ipbeja.pi.piproject.persistence;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Converts Date into int timestamps back and forth
 */
public class Converters {
    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}