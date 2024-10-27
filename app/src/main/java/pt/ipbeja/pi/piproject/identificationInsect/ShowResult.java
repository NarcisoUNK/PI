//package pt.ipbeja.pi.piproject.identificationInsect;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import android.text.method.ScrollingMovementMethod;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import pt.ipbeja.pi.piproject.R;
//import pt.ipbeja.pi.piproject.idkey.IdentificationKey;
//import pt.ipbeja.pi.piproject.util.Util;
//
//
//// se não reconhece a AppCompatActivity apagar pasta .idea
//public class ShowResult extends AppCompatActivity {
//
//    private static final int SAVE_IDENT = 1;
//
//    private String resultKey;
//    private String ordem;
//
//    private Button go;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_result);
//
//        Intent intent = getIntent();
//        resultKey = intent.getStringExtra("fragmentID");
//
//
//        try {
//            IdentificationKey idkey = IdentificationKey.getInstance(getApplicationContext());
//
//            this.ordem = idkey.getResult(resultKey).getOrdem();//12-8-4-2-1
//            TextView orderTxt = (TextView) findViewById(R.id.orderLbl);
//            orderTxt.setText(this.ordem);
//
//            TextView descTxt = (TextView) findViewById(R.id.descLbl);
//            descTxt.setMovementMethod(new ScrollingMovementMethod());
//            descTxt.setText(Util.removeSpaces(idkey.getResult(resultKey).getDescription()));
//
//            InputStream imageStream = getAssets().open(idkey.getResult(resultKey).getImageLocation());
//            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//            ImageView img = (ImageView) findViewById(R.id.imageView);
//            img.setImageBitmap(bitmap);
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//
//        go = findViewById(R.id.button7);
//
//        go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent();
//                intent.putExtra("fragmentID", true);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
//    }
//
//
//    public void onSaveQuitClick(View view)
//    {
//        Intent intent = new Intent(this, SaveIdentification.class);
//        intent.putExtra("fragmentID", this.resultKey);
//        intent.putExtra("order", this.ordem);
//        startActivityForResult(intent, RESULT_OK);
//    }
//
//    public void onQuitClick(View view)
//    {
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("finish", true);
//        setResult(RESULT_OK, resultIntent);
//        finish();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == SAVE_IDENT) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("finish", true);
//                setResult(RESULT_OK, resultIntent);
//                finish();
//            }
//        }
//    }
//
//}
