package pt.ipbeja.pi.piproject.listSavedInsects

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.persistence.Identification
import java.io.FileNotFoundException
import java.text.SimpleDateFormat

/**
 * Created by vxf on 2/2/18.
 */
class IdentificationAdapter(private val ctx: Context, objects: List<Identification?>) :
    ArrayAdapter<Identification?>(ctx, 0, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val ident = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listing, parent, false)
        }

        val orderTxt = convertView!!.findViewById<View>(R.id.orderTxt) as TextView
        val coordsTxt = convertView.findViewById<View>(R.id.coordsTxt) as TextView
        val dateTxt = convertView.findViewById<View>(R.id.dateTxt) as TextView
        val imageView = convertView.findViewById<View>(R.id.coverView) as ImageView

        orderTxt.text = ident!!.order
        coordsTxt.text = ident.dMSCoord
        dateTxt.text = (SimpleDateFormat()).format(ident.timestamp)


        try {
            val photoUri = Uri.parse(ident.photoURI)
            val `is` = ctx.contentResolver.openInputStream(photoUri)
            val image = BitmapFactory.decodeStream(`is`)
            imageView.setImageBitmap(image)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return convertView
    }
}