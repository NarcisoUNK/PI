//package pt.ipbeja.pi.piproject.identificationInsect;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//import com.github.chrisbanes.photoview.PhotoView;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import pt.ipbeja.pi.piproject.R;
//
//public class ImageZoom extends AppCompatActivity {
//
//    private Button go;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_zoom);
//
//        Intent intent = getIntent();
//        String optionImage = intent.getStringExtra("optionImage");
//
//        PhotoView photoView = findViewById(R.id.zoom_photo);
//
//        try {
//            InputStream imageStream = getAssets().open(optionImage);
//            Bitmap b = BitmapFactory.decodeStream(imageStream);
//
//            photoView.setImageBitmap(b);
//
//            imageStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        go = findViewById(R.id.button10);
//
//        go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent backIntent = new Intent();
//                backIntent.putExtra("fragmentID", true);
//                setResult(RESULT_OK, backIntent);
//                finish();
//            }
//        });
//    }
//
//}
