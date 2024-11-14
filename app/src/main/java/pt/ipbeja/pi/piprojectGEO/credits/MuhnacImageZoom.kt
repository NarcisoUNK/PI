package pt.ipbeja.pi.piprojectGEO.credits

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.pi.piproject.R

class MuhnacImageZoom : AppCompatActivity() {
    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muhnac_image_zoom)

        go = findViewById(R.id.button7)

        go.setOnClickListener(View.OnClickListener { //                Correção de navegaçao com o botao de retroceder
            //                Intent intent = new Intent(MuhnacImageZoom.this, Credits.class);
            //                startActivity(intent);
            finish()
        })
    }
}