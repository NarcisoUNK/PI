package pt.ipbeja.pi.piprojectGEO.persistence

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
    fun insertIdentification(identification: Identification) // Non-nullable parameter

    @Delete
    fun deleteIdentification(identification: Identification) // Non-nullable parameter

    @Update
    fun updateOrderInIdentification(identification: Identification) // Non-nullable parameter

    @get:Query("SELECT * FROM identification")
    val allIdentifications: Array<Identification?>

    @get:Query("SELECT COUNT(*) FROM identification")
    val identificationCount: Int
}