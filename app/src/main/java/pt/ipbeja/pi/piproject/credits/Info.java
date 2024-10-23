//package pt.ipbeja.pi.piproject.credits;
//
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.View;
//import android.webkit.WebView;
//import android.widget.Button;
//
//import pt.ipbeja.pi.piproject.R;
//
//public class Info extends AppCompatActivity {
//
//   private Button go;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_info);
//
//        go = findViewById(R.id.button14);
//
//        go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
////                Correção de navegaçao com o botao de retroceder
// //               Intent intent = new Intent(Info.this, Credits.class);
// //               startActivity(intent);
//                finish();
//            }
//        });
//
//        // get our html content
//        String htmlAsString = getString(R.string.Info_About_APP); // used by WebView
//
//        WebView webView = (WebView) findViewById(R.id.webView);
//        webView.loadDataWithBaseURL(null, htmlAsString, "text/html",
//                "utf-8", null);
//    }
//}
