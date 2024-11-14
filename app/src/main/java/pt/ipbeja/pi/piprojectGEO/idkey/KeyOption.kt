package pt.ipbeja.pi.piprojectGEO.idkey

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

/**
 * An option with a possible answer to a question node, the option has a short description, a long
 * description and an image to illustrate
 */
class KeyOption

/**
 * Creates an option object
 *
 * @param gotoId the node to which this option connects to
 * @param description a long description of the option
 * @param imageLocation the location of the image of this option
 * @param text short description of this option
 */(
    /**
     * Get the id of the node to which this option connects
     *
     * @return the id of the node to which this option connects
     */
    val gotoId: String,
    /**
     * Get the long description of the option
     *
     * @return the long description of the option
     */
    val description: String,
    /**
     * Get the filename of the image of this option
     *
     * @return the filename of the image of this option
     */
    val imageLocation: String,
    /**
     * Get the short description of the option
     *
     * @return the short description of the option
     */
    val text: String
) {
    /**
     * Load the image of this option into a usable bitmap object
     *
     * @param ctx the activity context
     * @return a usable bitmap of the image of this option
     * @throws IOException in case the image could not be loaded
     */
    @Throws(IOException::class)
    fun loadImage(ctx: Context): Bitmap {
        val `is` = ctx.assets.open(this.imageLocation)

        return BitmapFactory.decodeStream(`is`)
    }

    /**
     * Get the string representation of the option
     *
     * @return the string representation of the option
     */
    override fun toString(): String {
        return "KeyOption{" +
                "gotoId='" + gotoId + '\'' +
                ", description='" + description + '\'' +
                ", imageLocation='" + imageLocation + '\'' +
                ", text='" + text + '\'' +
                '}'
    }
}