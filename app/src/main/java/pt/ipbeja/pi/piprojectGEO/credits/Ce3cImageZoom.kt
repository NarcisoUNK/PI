package pt.ipbeja.pi.piprojectGEO.credits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import pt.ipbeja.pi.piproject.R

class Ce3cImageZoom : AppCompatActivity() {

    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ce3c_image_zoom)

        go = findViewById(R.id.button7)

        go.setOnClickListener {
            // Correção de navegação com o botão de retroceder
            // val intent = Intent(this@Ce3cImageZoom, Credits::class.java)
            // startActivity(intent)
            finish()
        }
    }
}
