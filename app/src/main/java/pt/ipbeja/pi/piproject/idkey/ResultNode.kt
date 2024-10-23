package pt.ipbeja.pi.piproject.idkey

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

/**
 * A final node in a classification with relevant information of the conclusion of the
 * identification. It may inform the order, a long description and an illustrative image.
 */
class ResultNode

/**
 * Creates a result node object
 *
 * @param id the node id
 * @param ordem the order of the classified subject
 * @param description a long description of the classified subject
 * @param imageLocation image of the subject
 */(
    /**
     * Get the node id
     *
     * @return the node id
     */
    val id: String,
    /**
     * Get the classification
     *
     * @return the classification
     */
    val ordem: String,
    /**
     * Get the long description of the classification
     *
     * @return the long description of the classification
     */
    val description: String,
    /**
     * Get the image of the subject
     *
     * @return the image of the subject
     */
    val imageLocation: String
) {
    /**
     * Loads a usable bitmap representing the classified subject for comparison
     *
     * @param ctx the activity context
     * @return the bitmap of the image
     * @throws IOException if the image could not be loaded
     */
    @Throws(IOException::class)
    fun loadImage(ctx: Context): Bitmap {
        val `is` = ctx.assets.open(this.imageLocation)

        return BitmapFactory.decodeStream(`is`)
    }
}