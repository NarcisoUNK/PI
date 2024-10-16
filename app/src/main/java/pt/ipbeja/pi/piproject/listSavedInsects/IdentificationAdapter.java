package pt.ipbeja.pi.piproject.listSavedInsects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import pt.ipbeja.pi.piproject.R;
import pt.ipbeja.pi.piproject.persistence.Identification;

/**
 * Created by vxf on 2/2/18.
 */

public class IdentificationAdapter extends ArrayAdapter<Identification>
{

    private Context ctx;

    public IdentificationAdapter(@NonNull Context context, @NonNull List<Identification> objects) {
        super(context, 0, objects);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Identification ident = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listing, parent, false);
        }

        TextView orderTxt = (TextView) convertView.findViewById(R.id.orderTxt);
        TextView coordsTxt = (TextView) convertView.findViewById(R.id.coordsTxt);
        TextView dateTxt = (TextView) convertView.findViewById(R.id.dateTxt);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.coverView);

        orderTxt.setText(ident.getOrder());
        coordsTxt.setText(ident.getDMSCoord());
        dateTxt.setText((new SimpleDateFormat()).format(ident.getTimestamp()));


        try {
            Uri photoUri = Uri.parse(ident.getPhotoURI());
            InputStream is = this.ctx.getContentResolver().openInputStream(photoUri);
            Bitmap image = BitmapFactory.decodeStream(is);
            imageView.setImageBitmap(image);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return convertView;
    }
}
