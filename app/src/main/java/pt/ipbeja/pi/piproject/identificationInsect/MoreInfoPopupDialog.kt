package pt.ipbeja.pi.piproject.identificationInsect

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.idkey.KeyOption
import java.io.IOException

/**
 * Created by Vicenzo on 01-02-18.
 */
class MoreInfoPopupDialog //Log.e("$$$$$$$$$$$$->", "Info");
    (var activity: Activity, var option: KeyOption) : Dialog(activity) {
    var dialog: Dialog? = null
    var close: Button? = null

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_moreinfopopup)

        close = findViewById<View>(R.id.closeBtn) as Button

        close!!.setOnClickListener { dismiss() }


        val image = findViewById<View>(R.id.imgView) as ImageView

        val assMan = activity.assets
        val imagePath = option.imageLocation
        try {
            val ims = assMan.open(imagePath)
            val bitmap = BitmapFactory.decodeStream(ims)
            image.setImageBitmap(bitmap)
            ims.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val descText = findViewById<View>(R.id.descLbl) as TextView
        val description = option.description
        descText.text = description

        image.setOnClickListener {
            val intent = Intent(
                this@MoreInfoPopupDialog.activity,
                ImageZoom::class.java
            )
            intent.putExtra(
                "optionImage",
                option.imageLocation
            )
            activity.startActivity(intent)
        }
    }
}