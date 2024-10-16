package pt.ipbeja.pi.piproject.credits;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pt.ipbeja.pi.piproject.startUpApp.MainActivity;
import pt.ipbeja.pi.piproject.R;


public class Credits extends AppCompatActivity {

    private Button go, go2, go3, go4, go5, go6, go7, go8, go9, go10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        go = findViewById(R.id.button13);
        go2 = findViewById(R.id.button_privacy_policy);
        go3 = findViewById(R.id.button9);
        go4 = findViewById(R.id.button_ip_beja);
        go5 = findViewById(R.id.button_targis);
        go6 = findViewById(R.id.button_pca);
        go7 = findViewById(R.id.button_muhnac);
        go8 = findViewById(R.id.button_ce3c);
        go9 = findViewById(R.id.button_cmfcr);
        go10 = findViewById(R.id.button_leiden);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                finish();

            }
        });

        go2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, PrivacyPolicyInfo.class);
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.setData(Uri.parse("https://www.plataforma.edu.pt/politica-de-privacidade"));
                startActivity(intent);
            }
        });


        go3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Credits.this, Info.class);
                startActivity(intent);
            }
        });

        go4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, IPBejaImageZoom.class);
                startActivity(intent);
            }
        });

        go5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, TagisImageZoom.class);
                startActivity(intent);
            }
        });

        go6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, PCAImageZoom.class);
                startActivity(intent);
            }
        });

        go7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, MuhnacImageZoom.class);
                startActivity(intent);
            }
        });

        go8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, Ce3cImageZoom.class);
                startActivity(intent);
            }
        });

        go9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, CMFCRImageZoom.class);
                startActivity(intent);
            }
        });

        go10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Credits.this, LeidenImageZoom.class);
                startActivity(intent);
            }
        });
    }
}