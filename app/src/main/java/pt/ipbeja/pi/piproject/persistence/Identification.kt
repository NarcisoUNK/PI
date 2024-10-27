package pt.ipbeja.pi.piproject.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import pt.ipbeja.pi.piproject.util.Coordinates
import java.util.Date

/**
 * Identification object to store in the database
 */
@Entity
class Identification
/**
 * Creates an identification record
 *
 * @param keyId key id in the XML key definition
 * @param order the subject order found
 * @param latitude latitude of the observation
 * @param longitude longitude of the observation
 * @param timestamp date of the observation
 * @param photoURI URI of the associated image
 */(
    /**
     * Get the key id in the XML
     *
     * @return the key id in the XML
     */
    var keyId: String,
    /**
     * Get the order of the found subject
     *
     * @return the order of the found subject
     */
    var order: String,
    /**
     * Get the latitude of the observation
     *
     * @return the latitude of the observation
     */
    var latitude: Double,
    /**
     * Get the longitude of the observation
     *
     * @return the longitude of the observation
     */
    var longitude: Double,
    /**
     * Get the date of the observation
     *
     * @return the date of the observation
     */
    var timestamp: Date,
    /**
     * Get the URI string of the associated image
     * @return the URI string of the associated image
     */
    var photoURI: String
) {
    /**
     * Get the id of the record
     *
     * @return the id of the record
     */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    val dMSCoord: String
        /**
         * Get the coordinates of the observation in human readable coordinates
         *
         * @return the coordinates of the observation in human readable coordinates
         */
        get() = Coordinates.anglesToDMS(
            this.latitude,
            longitude
        )


    override fun toString(): String {
        return "Identification{" +
                "id=" + id +
                ", keyId='" + keyId + '\'' +
                ", order='" + order + '\'' +
                ", coords=" + this.dMSCoord +
                ", timestamp='" + timestamp + '\'' +
                ", uri='" + photoURI + "'" +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as Identification
        return id == that.id
    }

    override fun hashCode(): Int {
        return id
    }
}