package pt.ipbeja.pi.piproject.credits;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import pt.ipbeja.pi.piproject.R;

//import android.support.v7.app.ActionBarActivity;

public class PrivacyPolicyInfo extends AppCompatActivity {

    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy_info);

        go = findViewById(R.id.button11);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
///               Intent intent = new Intent(PrivacyPolicyInfo.this, Credits.class);
 //               startActivity(intent);/                Correção de navegaçao com o botao de retroceder
                //
                finish();
            }
        });

        // get the html content
        String htmlAsString = getString(R.string.Privacy_Policy_INFO);  // used by WebView

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html",
                "utf-8", null);

    }

}
