package pt.ipbeja.pi.piprojectGEO.credits

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.pi.piproject.R

class Info : AppCompatActivity() {
    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        go = findViewById(R.id.button14)

        go.setOnClickListener(View.OnClickListener { //                Correção de navegaçao com o botao de retroceder
            //               Intent intent = new Intent(Info.this, Credits.class);
            //               startActivity(intent);
            finish()
        })

        // get our html content
        val htmlAsString = getString(R.string.Info_About_APP) // used by WebView

        val webView = findViewById<View>(R.id.webView) as WebView
        webView.loadDataWithBaseURL(
            null, htmlAsString, "text/html",
            "utf-8", null
        )
    }
}