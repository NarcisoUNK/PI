package pt.ipbeja.pi.piproject.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Database object to connect to the database
 *
 * reference: https://developer.android.com/training/data-storage/room/index.html
 */
@Database(version = 1, entities = [Identification::class], exportSchema = false)
@TypeConverters(
    Converters::class
)
abstract class MyIdentificationsDb : RoomDatabase() {
    abstract fun identificationDao(): IdentificationDao?
}