package pt.ipbeja.pi.piprojectGEO.credits

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.pi.piproject.R

class Credits : AppCompatActivity() {
    private lateinit var go: Button
    private lateinit var go2: Button
    private lateinit var go3: Button
    private lateinit var go4: Button
    private lateinit var go5: Button
    private lateinit var go6: Button
    private lateinit var go7: Button
    private lateinit var go8: Button
    private lateinit var go9: Button
    private lateinit var go10: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        go = findViewById(R.id.button13)
        go2 = findViewById(R.id.button_privacy_policy)
        go3 = findViewById(R.id.button9)
        go4 = findViewById(R.id.button_ip_beja)
        go5 = findViewById(R.id.button_targis)
        go6 = findViewById(R.id.button_pca)
        go7 = findViewById(R.id.button_muhnac)
        go8 = findViewById(R.id.button_ce3c)
        go9 = findViewById(R.id.button_cmfcr)
        go10 = findViewById(R.id.button_leiden)

        go.setOnClickListener(View.OnClickListener { finish() })

        go2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, PrivacyPolicyInfo::class.java)
            //                intent.setAction(Intent.ACTION_VIEW);
            //                intent.addCategory(Intent.CATEGORY_BROWSABLE);
            //                intent.setData(Uri.parse("https://www.plataforma.edu.pt/politica-de-privacidade"));
            startActivity(intent)
        })


        go3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, Info::class.java)
            startActivity(intent)
        })

        go4.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, IPBejaImageZoom::class.java)
            startActivity(intent)
        })

        go5.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, TagisImageZoom::class.java)
            startActivity(intent)
        })

        go6.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, PCAImageZoom::class.java)
            startActivity(intent)
        })

        go7.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, MuhnacImageZoom::class.java)
            startActivity(intent)
        })

        go8.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, Ce3cImageZoom::class.java)
            startActivity(intent)
        })

        go9.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, CMFCRImageZoom::class.java)
            startActivity(intent)
        })

        go10.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Credits, LeidenImageZoom::class.java)
            startActivity(intent)
        })
    }
}