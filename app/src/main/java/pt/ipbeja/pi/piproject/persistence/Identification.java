package pt.ipbeja.pi.piproject.persistence;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Date;
import java.util.Objects;

import pt.ipbeja.pi.piproject.util.Coordinates;

/**
 * Identification object to store in the database
 */
@Entity
public class Identification {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String keyId;
    public String order;
    public double latitude;
    public double longitude;
    public Date timestamp;
    public String photoURI;

    /**
     * Creates an identification record
     *
     * @param keyId key id in the XML key definition
     * @param order the subject order found
     * @param latitude latitude of the observation
     * @param longitude longitude of the observation
     * @param timestamp date of the observation
     * @param photoURI URI of the associated image
     */
    public Identification(String keyId, String order, double latitude, double longitude, Date timestamp, String photoURI) {
        this.keyId = keyId;
        this.order = order;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.photoURI = photoURI;
    }

    /**
     * Get the id of the record
     *
     * @return the id of the record
     */
    public int getId() {
        return id;
    }

    /**
     * Get the key id in the XML
     *
     * @return the key id in the XML
     */
    public String getKeyId() {
        return keyId;
    }

    /**
     * Get the order of the found subject
     *
     * @return the order of the found subject
     */
    public String getOrder() {
        return order;
    }

    /**
     * Get the latitude of the observation
     *
     * @return the latitude of the observation
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get the longitude of the observation
     *
     * @return the longitude of the observation
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get the coordinates of the observation in human readable coordinates
     *
     * @return the coordinates of the observation in human readable coordinates
     */
    public String getDMSCoord()
    {
        return Coordinates.anglesToDMS(this.getLatitude(), this.getLongitude());
    }

    /**
     * Get the date of the observation
     *
     * @return the date of the observation
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Get the URI string of the associated image
     * @return the URI string of the associated image
     */
    public String getPhotoURI() {return photoURI; }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }



    @Override
    public String toString() {
        return "Identification{" +
                "id=" + id +
                ", keyId='" + keyId + '\'' +
                ", order='" + order + '\'' +
                ", coords=" + this.getDMSCoord() +
                ", timestamp='" + timestamp + '\'' +
                ", uri='" + photoURI + "'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identification that = (Identification) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return id;
    }
}
