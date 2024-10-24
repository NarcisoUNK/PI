package pt.ipbeja.pi.piproject.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Data access object for identifications
 */
@Dao
interface IdentificationDao {
    /**
     * Inserts a record of an identification in the database
     *
     * @param identification the identification to insert
     */
    @Insert
    fun insertIdentification(identification: Identification?)

    /**
     * Removes an identification from the the database
     *
     * @param identification the identification to remove
     */
    @Delete
    fun deleteIdentification(identification: Identification?)

    @Update
    fun updateOrderInIdentification(identification: Identification?)

    @get:Query("SELECT * FROM identification")
    val allIdentifications: Array<Identification?>?

    @get:Query("SELECT COUNT(*) FROM identification")
    val identificationCount: Int
}