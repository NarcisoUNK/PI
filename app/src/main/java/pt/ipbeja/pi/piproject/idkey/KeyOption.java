package pt.ipbeja.pi.piproject.idkey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * An option with a possible answer to a question node, the option has a short description, a long
 * description and an image to illustrate
 */
public class KeyOption
{
    private String gotoId;
    private String description;
    private String imageLocation;
    private String text;

    /**
     * Creates an option object
     *
     * @param gotoId the node to which this option connects to
     * @param description a long description of the option
     * @param imageLocation the location of the image of this option
     * @param text short description of this option
     */
    public KeyOption(String gotoId, String description, String imageLocation, String text) {
        this.gotoId = gotoId;
        this.description = description;
        this.imageLocation = imageLocation;
        this.text = text;
    }

    /**
     * Get the id of the node to which this option connects
     *
     * @return the id of the node to which this option connects
     */
    public String getGotoId() {
        return gotoId;
    }

    /**
     * Get the long description of the option
     *
     * @return the long description of the option
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the filename of the image of this option
     *
     * @return the filename of the image of this option
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Load the image of this option into a usable bitmap object
     *
     * @param ctx the activity context
     * @return a usable bitmap of the image of this option
     * @throws IOException in case the image could not be loaded
     */
    public Bitmap loadImage(Context ctx) throws IOException {
        InputStream is = ctx.getAssets().open(this.imageLocation);

        return BitmapFactory.decodeStream(is);
    }

    /**
     * Get the short description of the option
     *
     * @return the short description of the option
     */
    public String getText() {
        return text;
    }

    /**
     * Get the string representation of the option
     *
     * @return the string representation of the option
     */
    @Override
    public String toString() {
        return "KeyOption{" +
                "gotoId='" + gotoId + '\'' +
                ", description='" + description + '\'' +
                ", imageLocation='" + imageLocation + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}