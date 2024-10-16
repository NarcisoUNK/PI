package pt.ipbeja.pi.piproject.idkey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * A final node in a classification with relevant information of the conclusion of the
 * identification. It may inform the order, a long description and an illustrative image.
 */
public class ResultNode
{
    private String id;
    private String ordem;
    private String description;
    private String imageLocation;

    /**
     * Creates a result node object
     *
     * @param id the node id
     * @param ordem the order of the classified subject
     * @param description a long description of the classified subject
     * @param imageLocation image of the subject
     */
    public ResultNode(String id, String ordem, String description, String imageLocation)
    {
        this.id = id;
        this.ordem = ordem;
        this.description = description;
        this.imageLocation = imageLocation;
    }

    /**
     * Get the node id
     *
     * @return the node id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the classification
     *
     * @return the classification
     */
    public String getOrdem() {
        return ordem;
    }

    /**
     * Get the long description of the classification
     *
     * @return the long description of the classification
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the image of the subject
     *
     * @return the image of the subject
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Loads a usable bitmap representing the classified subject for comparison
     *
     * @param ctx the activity context
     * @return the bitmap of the image
     * @throws IOException if the image could not be loaded
     */
    public Bitmap loadImage(Context ctx) throws IOException {
        InputStream is = ctx.getAssets().open(this.imageLocation);

        return BitmapFactory.decodeStream(is);
    }
}
