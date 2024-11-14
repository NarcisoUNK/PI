package pt.ipbeja.pi.piprojectGEO.identificationInsect

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.chrisbanes.photoview.PhotoView
import pt.ipbeja.pi.piproject.R
import java.io.IOException

class ImageZoom : AppCompatActivity() {
    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_zoom)

        val intent = intent
        val optionImage = intent.getStringExtra("optionImage")

        val photoView = findViewById<PhotoView>(R.id.zoom_photo)

        try {
            val imageStream = assets.open(optionImage!!)
            val b = BitmapFactory.decodeStream(imageStream)

            photoView.setImageBitmap(b)

            imageStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        go = findViewById(R.id.button10)

        go.setOnClickListener(View.OnClickListener {
            val backIntent = Intent()
            backIntent.putExtra("fragmentID", true)
            setResult(RESULT_OK, backIntent)
            finish()
        })
    }
}