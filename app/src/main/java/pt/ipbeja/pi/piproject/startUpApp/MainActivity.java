//package pt.ipbeja.pi.piproject.startUpApp;
//
//import android.Manifest;
//
//import androidx.core.app.ActivityCompat;
//import androidx.room.Room;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//
//import pt.ipbeja.pi.piproject.identificationInsect.Classificacao;
//import pt.ipbeja.pi.piproject.listSavedInsects.MyIdentifications;
//import pt.ipbeja.pi.piproject.R;
//import pt.ipbeja.pi.piproject.credits.Credits;
//import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final int PERMISSIONS_READ_EXTERNAL_STORAGE = 1;
//
//   // @SuppressLint("WrongViewCast")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_main);
//        setInitialButtonState();
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setInitialButtonState();
//    }
//
//    private void setInitialButtonState() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MyIdentificationsDb db = Room.databaseBuilder(
//                        MainActivity.this.getApplicationContext(),
//                        MyIdentificationsDb.class,
//                        "myidentifications.db")
//                        .build();
//
//                final int idCount = db.identificationDao().getIdentificationCount();
//                //System.out.println(Integer.toString(idCount) + "Entries");
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Button myIdsButton = (Button) findViewById(R.id.button);
//                        myIdsButton.setEnabled(idCount > 0);
//                    }
//                });
//            }
//        }).start();
//    }
//
//    public void onClickToClassification(View view) {
//        Intent intent = new Intent(this, Classificacao.class);
//        intent.putExtra("fragmentID", "Q1");
//        startActivity(intent);
//    }
//
//    public void onClickToMyInsects(View view) {
//        if (ActivityCompat.checkSelfPermission(this.getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    PERMISSIONS_READ_EXTERNAL_STORAGE);
//        } else {
//            Intent intent = new Intent(this, MyIdentifications.class);
//            startActivity(intent);
//        }
//    }
//
//
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSIONS_READ_EXTERNAL_STORAGE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    Intent intent = new Intent(this, MyIdentifications.class);
//                    startActivity(intent);
//
//                } else {
//
//                    // finish();
//                }
//                return;
//            }
//        }
//    }
//
//    public void onClickToCredits(View view) {
//        Intent intent = new Intent(this, Credits.class);
//        startActivity(intent);
////        correcao de navegação creditos
////        finish();
//    }
//
//
//}
