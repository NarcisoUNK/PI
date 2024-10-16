package pt.ipbeja.pi.piproject.identificationInsect;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import pt.ipbeja.pi.piproject.R;
import pt.ipbeja.pi.piproject.idkey.KeyOption;

/**
 * Created by Vicenzo on 01-02-18.
 */

public class MoreInfoPopupDialog extends Dialog {
    public Activity activity;
    public Dialog dialog;
    public Button close;
    public KeyOption option;

    public MoreInfoPopupDialog(Activity a, KeyOption option) {
        super(a);
        //Log.e("$$$$$$$$$$$$->", "Info");
        this.activity = a;
        this.option = option;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_moreinfopopup);

        close = (Button) findViewById(R.id.closeBtn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });


        ImageView image = (ImageView) findViewById(R.id.imgView);

        AssetManager assMan = activity.getAssets();
        String imagePath = option.getImageLocation();
        try {
            InputStream ims = assMan.open(imagePath);
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            image.setImageBitmap(bitmap);
            ims.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        TextView descText = (TextView) findViewById(R.id.descLbl);
        String description = option.getDescription();
        descText.setText(description);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreInfoPopupDialog.this.activity, ImageZoom.class);
                intent.putExtra("optionImage", MoreInfoPopupDialog.this.option.getImageLocation());
                MoreInfoPopupDialog.this.activity.startActivity(intent);
            }
        });
    }
}
