package pt.ipbeja.pi.piproject.credits

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.pi.piproject.R

class CMFCRImageZoom : AppCompatActivity() {
    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cmfcrimage_zoom)

        go = findViewById(R.id.button7)

        go.setOnClickListener{

            //                Correção de navegaçao com o botao de retroceder
            //                Intent intent = new Intent(CMFCRImageZoom.this, Credits.class);
            //                startActivity(intent);
            finish()
        }
    }
}