package pt.ipbeja.pi.piprojectGEO.credits

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.pi.piproject.R

//import android.support.v7.app.ActionBarActivity;
class PrivacyPolicyInfo : AppCompatActivity() {
    private lateinit var go: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy_info)

        go = findViewById(R.id.button11)

        go.setOnClickListener(View.OnClickListener {
            /**               Intent intent = new Intent(PrivacyPolicyInfo.this, Credits.class); */
            /**               Intent intent = new Intent(PrivacyPolicyInfo.this, Credits.class); */

            //               startActivity(intent);/                Correção de navegaçao com o botao de retroceder
            //
            finish()
        })

        // get the html content
        val htmlAsString = getString(R.string.Privacy_Policy_INFO) // used by WebView

        val webView = findViewById<View>(R.id.webView) as WebView
        webView.loadDataWithBaseURL(
            null, htmlAsString, "text/html",
            "utf-8", null
        )
    }
}