package pt.ipbeja.pi.piproject.listSavedInsects

import android.annotation.SuppressLint
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

class IdentificationAdapter(private val ctx: Context, objects: List<Identification?>) :
    ArrayAdapter<Identification?>(ctx, 0, objects) {

    @SuppressLint("SimpleDateFormat")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val ident = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listing, parent, false)
        }

        val orderTxt = convertView!!.findViewById<TextView>(R.id.orderTxt)
        val coordsTxt = convertView.findViewById<TextView>(R.id.coordsTxt)
        val dateTxt = convertView.findViewById<TextView>(R.id.dateTxt)
        val imageView = convertView.findViewById<ImageView>(R.id.coverView)

        orderTxt.text = ident?.order
        coordsTxt.text = ident?.dMSCoord
        dateTxt.text = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(ident?.timestamp)

        // Tenta carregar a imagem do URI fornecido, caso a permissão esteja ativa
        try {
            val photoUri = Uri.parse(ident?.photoURI)
            val inputStream = ctx.contentResolver.openInputStream(photoUri)
            val image = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(image)
        } catch (e: SecurityException) {
            e.printStackTrace()
            // Exibe uma imagem de substituição caso o acesso ao URI falhe
            imageView.setImageResource(R.drawable.placeholder_image)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            imageView.setImageResource(R.drawable.placeholder_image)
        }

        return convertView
    }
}
