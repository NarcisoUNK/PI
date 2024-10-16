package pt.ipbeja.pi.piproject.credits;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pt.ipbeja.pi.piproject.R;

public class MuhnacImageZoom extends AppCompatActivity {

    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muhnac_image_zoom);

        go = findViewById(R.id.button7);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Correção de navegaçao com o botao de retroceder
//                Intent intent = new Intent(MuhnacImageZoom.this, Credits.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}
