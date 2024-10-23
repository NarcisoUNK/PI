package pt.ipbeja.pi.piproject.identificationInsect

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.pi.piproject.R
import pt.ipbeja.pi.piproject.idkey.IdentificationKey
import pt.ipbeja.pi.piproject.util.Util
import java.io.IOException

// se não reconhece a AppCompatActivity apagar pasta .idea
class ShowResult : AppCompatActivity() {
    private var resultKey: String? = null
    private var ordem: String? = null

    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_result)

        val intent = intent
        resultKey = intent.getStringExtra("fragmentID")


        try {
            val idkey = IdentificationKey.getInstance(applicationContext)

            if (idkey != null) {
                this.ordem = idkey.getResult(resultKey.toString())?.ordem
            } //12-8-4-2-1
            val orderTxt = findViewById<View>(R.id.orderLbl) as TextView
            orderTxt.text = ordem

            val descTxt = findViewById<View>(R.id.descLbl) as TextView
            descTxt.movementMethod = ScrollingMovementMethod()
            if (idkey != null) {
                descTxt.text = Util.removeSpaces(idkey.getResult(resultKey.toString())!!.description)
            }

            val imageStream = resultKey?.let { idkey?.getResult(it)?.let { assets.open(it.imageLocation) } }
            val bitmap = BitmapFactory.decodeStream(imageStream)
            val img = findViewById<View>(R.id.imageView) as ImageView
            img.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        go = findViewById(R.id.button7)

        go.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra("fragmentID", true)
            setResult(RESULT_OK, intent)
            finish()
        })
    }


    fun onSaveQuitClick(view: View?) {
        val intent = Intent(this, SaveIdentification::class.java)
        intent.putExtra("fragmentID", this.resultKey)
        intent.putExtra("order", this.ordem)
        startActivityForResult(intent, RESULT_OK)
    }

    fun onQuitClick(view: View?) {
        val resultIntent = Intent()
        resultIntent.putExtra("finish", true)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check which request we're responding to
        if (requestCode == SAVE_IDENT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                val resultIntent = Intent()
                resultIntent.putExtra("finish", true)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    companion object {
        private const val SAVE_IDENT = 1
    }
}