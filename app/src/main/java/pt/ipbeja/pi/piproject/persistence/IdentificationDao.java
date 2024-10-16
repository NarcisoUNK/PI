package pt.ipbeja.pi.piproject.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Data access object for identifications
 */
@Dao
public interface IdentificationDao {
    /**
     * Inserts a record of an identification in the database
     *
     * @param identification the identification to insert
     */
    @Insert
    public void insertIdentification(Identification identification);

    /**
     * Removes an identification from the the database
     *
     * @param identification the identification to remove
     */
    @Delete
    public void deleteIdentification(Identification identification);

    @Update
    public void updateOrderInIdentification(Identification identification);

    /**
     * Retrieves all the identifications in the database
     *
     * @return all the identifications in the database
     */
    @Query("SELECT * FROM identification")
    public Identification[] getAllIdentifications();

    @Query("SELECT COUNT(*) FROM identification")
    public int getIdentificationCount();
}
