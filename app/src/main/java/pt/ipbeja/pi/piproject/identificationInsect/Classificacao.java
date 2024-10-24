//package pt.ipbeja.pi.piproject.identificationInsect;
//
//import android.content.Intent;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
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
//import pt.ipbeja.pi.piproject.idkey.QuestionNode;
//import pt.ipbeja.pi.piproject.idkey.ResultNode;
//
//public class Classificacao extends AppCompatActivity {
//
//    private final static int NEXT_STEP = 1;
//    private final static int FINAL_STEP = 2;
//
//    private String currentFragmentID;
//    private String optionAEndpoint;
//    private String optionBEndpoint;
//
//    private QuestionNode node;
//    private Button go;
//    private Button go2;
//    private Object MenuItem;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_classificacao);
//        Intent intent = getIntent();
//        currentFragmentID = intent.getStringExtra("fragmentID");
//
//        go = findViewById(R.id.button7);
//        go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//              Intent backIntent = new Intent();
//              backIntent.putExtra("fragmentID", true);
//              setResult(RESULT_OK, backIntent);
//              finish();
//            }
//        });
//
//        go2 = findViewById(R.id.button3);
//        go2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                Intent backHome = new Intent();
//                backHome.putExtra("finish", true);
//                setResult(RESULT_OK, backHome);
//                finish();
//            }
//        });
//
//
//        try {
//            IdentificationKey key = IdentificationKey.getInstance(getApplicationContext());
//
//
//            node = key.getQuestion(currentFragmentID);
//
//            TextView txt = findViewById(R.id.txt);
//            txt.setText(node.getQuestion());
//
//            Button btnA = findViewById(R.id.optionA);
//            btnA.setText(node.getOptionA().getText());
//
//            Button btnB = findViewById(R.id.optionB);
//            btnB.setText(node.getOptionB().getText());
//
//            this.optionAEndpoint = node.getOptionA().getGotoId();
//            this.optionBEndpoint = node.getOptionB().getGotoId();
//
//            ImageView imgA = findViewById(R.id.imageOptionA);
//            ImageView imgB = findViewById(R.id.imageOptionB);
//
//            this.setImage(imgA, node.getOptionA().getImageLocation());
//            this.setImage(imgB, node.getOptionB().getImageLocation());
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    protected void goToNextQuestion(String newFragmentID) {
//        try {
//            IdentificationKey key = IdentificationKey.getInstance(getApplicationContext());
//            if(key.isQuestion(newFragmentID))
//            {
//                // Intent intent = getIntent();
//                Intent intent = new Intent(this, Classificacao.class);
//                intent.putExtra("fragmentID", newFragmentID);
//                // startActivity(intent);
//
//                startActivityForResult(intent, NEXT_STEP);
//            }
//            else
//            {
//                ResultNode node = key.getResult(newFragmentID);
//                System.out.println(node.getOrdem());
//                String order = node.getId();
//
//                Intent resultActivityIntent = new Intent(this, ShowResult.class);
//                resultActivityIntent.putExtra("fragmentID", order);
//                // startActivity(resultActivityIntent
//
//                startActivityForResult(resultActivityIntent, NEXT_STEP);
//            }
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void onOptionAClick(View view)
//    {
//        goToNextQuestion(this.optionAEndpoint);
//    }
//
//    public void onOptionBClick(View view)
//    {
//        goToNextQuestion(this.optionBEndpoint);
//    }
//
//   /* public void onButtonHintClick(View view) {
//        KeyOption option = null;
//        try{
//            InputStream is = getApplicationContext().getAssets().open("chave.xml");
//
//            IdentificationKey key = IdentificationKey.loadXML(is);
//
//            is.close();
//            switch(view.getId())
//            {
//                case R.id.hintButtonA:
//                    option = key.getQuestion(currentFragmentID).getOptionA();
//                    break;
//                case R.id.hintButtonB:
//                    option = key.getQuestion(currentFragmentID).getOptionB();
//                    break;
//            }
//            MoreInfoPopupDialog customDialog =new MoreInfoPopupDialog(this, option);
//            customDialog.show();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }*/
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == NEXT_STEP) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                // The user picked a contact.
//                // The Intent's data Uri identifies which contact was selected.
//                if(data.getBooleanExtra("finish", false))
//                {
//                    setResult(RESULT_OK, data);
//                    finish();
//                }
//            }
//        }
//    }
//
//    public void onImageAClick(View view) {
//        Intent intent = new Intent(this, ImageZoom.class);
//        intent.putExtra("optionImage", this.node.getOptionA().getImageLocation());
//        this.startActivity(intent);
//    }
//
//    public void onImageBClick(View view) {
//        Intent intent = new Intent(this, ImageZoom.class);
//        intent.putExtra("optionImage", this.node.getOptionB().getImageLocation());
//        this.startActivity(intent);
//    }
//
//    private void setImage(ImageView image, String imagePath)
//    {
//        AssetManager assMan = this.getAssets();
//
//        try {
//            InputStream ims = assMan.open(imagePath);
//            Bitmap bitmap = BitmapFactory.decodeStream(ims);
//            image.setImageBitmap(bitmap);
//            ims.close();
//        }
//        catch(IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
//}
