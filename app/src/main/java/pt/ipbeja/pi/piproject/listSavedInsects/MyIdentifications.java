package pt.ipbeja.pi.piproject.listSavedInsects;

import androidx.appcompat.widget.PopupMenu;
import androidx.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import pt.ipbeja.pi.piproject.startUpApp.MainActivity;
import pt.ipbeja.pi.piproject.R;
import pt.ipbeja.pi.piproject.idkey.IdentificationKey;
import pt.ipbeja.pi.piproject.persistence.Identification;
import pt.ipbeja.pi.piproject.persistence.MyIdentificationsDb;
import pt.ipbeja.pi.piproject.util.Util;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

//import android.media.ExifInterface;

public class MyIdentifications extends AppCompatActivity {

    private static final int SAVE_IDENT = 1;
    private static final int PICK_FROM_GALLERY = 101;

    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_identifications);

        this.loadList();

        go = findViewById(R.id.button12);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadList() {
        new Thread() {
            @Override
            public void run() {

                final MyIdentificationsDb db = Room.databaseBuilder(
                        MyIdentifications.this.getApplicationContext(),
                        MyIdentificationsDb.class,
                        "myidentifications.db")
                        .build();

                final Identification[] idents = db.identificationDao().getAllIdentifications();

                // ArrayAdapter adapter = new ArrayAdapter<String>(this,R.id.identsLst , idents);

                // Construct the data source
                final ArrayList<Identification> arrayOfListings = new ArrayList<Identification>();
                // Create the adapter to convert the array to views
                final IdentificationAdapter adapter = new IdentificationAdapter(MyIdentifications.this, arrayOfListings);

                class ListAdderRunnable implements Runnable {
                    Identification[] toAdd;
                    IdentificationAdapter adapter;

                    ListAdderRunnable(Identification[] array, IdentificationAdapter adapter) {
                        this.toAdd = array;
                        this.adapter = adapter;
                    }

                    public void run() {
                        // Attach the adapter to a ListView
                        ListView identsLst = (ListView) findViewById(R.id.identsLst);
                        identsLst.setAdapter(adapter);

                        for (Identification ident : toAdd) {
                            adapter.add(ident);
                        }

                        setListener(identsLst);
                    }

                    // https://stackoverflow.com/questions/17851687/how-to-handle-the-click-event-in-listview-in-android
                    private void setListener(final ListView insectsListView) {
                        insectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                Identification item = (Identification) insectsListView.getItemAtPosition(position);
                                //changeColor(view, Color.GRAY);
                                view.setBackgroundColor(Color.LTGRAY);

                                //showDialogDeleteSend(identsLst, item);
                                showPopupMenu(insectsListView, view, item);
                                //changeColor(view, Color.WHITE);

                            }
                        });
                    }


                    private void showPopupMenu(final ListView insectsListView, final View view, final Identification identification) {
                        // https://developer.android.com/guide/topics/ui/menus#PopupMenu

//                        String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry",
//                                "WebOS", "Ubuntu", "Windows7", "Max OS X"};
//
//
//                        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                                R.layout.itemsActions_listview, mobileArray);
//
//                        list = (ListView) findViewById(R.id.mobile_list);
//                        list.setAdapter(adapter);

                        PopupMenu popup = new PopupMenu(MyIdentifications.this, view);

                        popup.getMenuInflater().inflate(R.menu.myinsects_action_menu, popup.getMenu());
                        popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                            @Override
                            public void onDismiss(PopupMenu menu) {
                                view.setBackgroundColor(Color.RED);

                            }
                        });
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.idmoreinfo:
                                        showMoreInformation(identification);
                                        return true;
                                    case R.id.idorder:
                                        changeOrder();
                                        return true;
                                    case R.id.idsend:
                                        sendEmailWithImageFromList(identification);
                                        return true;
                                    case R.id.iddelete:
                                        deleteItem(item, identification);
                                        return true;
                                    case R.id.idcancel:
                                        return true;
                                    default:
                                        return false;
                                }
                            }

                            @RequiresApi(api = Build.VERSION_CODES.O)
                            private void showMoreInformation(Identification identification) {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MyIdentifications.this);

                                builder.setTitle(R.string.moreinfotext);
                                String resultId = identification.getKeyId();
                                try {
                                    String description = IdentificationKey.getInstance(MyIdentifications.this).getResult(resultId).getDescription();

                                    builder.setMessage(Util.removeSpaces(description));
                                    builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // nothing to do. Just close
                                                }
                                            }
                                    );
                                    builder.setPositiveButton("+Info", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int wich) {
                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://drive.google.com/file/d/0B1REHtVuyBybX1BRcGRrWnc5S28/view"));
                                            startActivity(browserIntent);
                                        }
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    TextView messageText = (TextView) alert.findViewById(android.R.id.message);
                                    assert messageText != null;
                                    messageText.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            private void changeOrder() {
                                OrderPopupMenu m = new OrderPopupMenu(MyIdentifications.this,
                                        view, identification, db, idents,
                                        adapter, insectsListView);
                                m.show();
                            }

                            private void deleteItem(final MenuItem item, final Identification identification) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        db.identificationDao().deleteIdentification(identification);
                                        //arrayOfListings.remove(item);
                                        adapter.notifyDataSetChanged();
                                        insectsListView.invalidate();
                                    }
                                }.start();
                                finish();
                                startActivity(getIntent());
                            }
                        });
                        popup.show();

                    }

//                    private void showDialogDeleteSend(final ListView identsLst, final Identification item) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MyIdentifications.this);
//
//                        builder.setTitle("Apagar ou Enviar");
//
//                        builder.setMessage(item.getOrder());
//                        builder.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Toast.makeText(MyIdentifications.this, "Apagar", 1000).show();
//
//                                        //https://stackoverflow.com/questions/2558591/remove-listview-items-in-android
//
//                                        new Thread() {
//                                            @Override
//                                            public void run() {
//                                                db.identificationDao().deleteIdentification(item);
//                                                arrayOfListings.remove(item);
//                                                adapter.notifyDataSetChanged();
//                                                identsLst.invalidate();
//                                            }
//                                        }.start();
//                                    }
//                                }
//                        );
//                        builder.setNeutralButton("Enviar", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                       // Toast.makeText(MyIdentifications.this, item.getPhotoURI(), 1000).show();
//                                        sendEmailWithImageFromList(item);
//                                    }
//                                }
//                        );
//                        builder.setNegativeButton("Cancelar", null /* nothing to do*/);
//                        AlertDialog alert = builder.create();
//                        alert.show();
//                    }


                }
                runOnUiThread(new ListAdderRunnable(idents, adapter));

            }

                /*// Attach the adapter to a ListView
                ListView identsLst = (ListView) findViewById(R.id.identsLst);
                identsLst.setAdapter(adapter);

                for (Identification ident : idents){
                    adapter.add(ident);

                    Log.d("XXX", ident.toString());
                }*/

        }.start();

    }


    private void sendEmailWithImageFromList(Identification item) {
        Uri imageURI = Uri.parse(item.getPhotoURI());
        double latitude = item.getLatitude();
        double longitude = item.getLongitude();


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(item.getTimestamp());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeAndDate = df.format(calendar.getTime());


        // https://www.google.com/maps/search/?api=1&query=58.698017,-152.52206

        //String emailAddress = getResources().getString(R.string.emailAddress);
        String emailAddress = "info@plataforma.edu.pt";


        //String emailSubject = getResources().getString(R.string.emailSubject) + " - " + timeAndDate;
        String emailSubject = item.getOrder() + " - " + timeAndDate;
        //String emailSubject = getResources().getString(R.string.emailSubject) + " - " + timeAndDate;
        String emailBody = getResources().getString(R.string.emailBody) + "Latitude: " + latitude + " Longitude: " + longitude +
                "\n\n https://www.google.com/maps/search/?api=1&query=" +
                latitude + "," + longitude;

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailAddress});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);

        if (imageURI != null) {
            emailIntent.putExtra(Intent.EXTRA_STREAM, imageURI);
        }

        this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
    }


    //Bottom Send Email
    /*public void onSendEmailClick(View view) {
        Intent intent = new Intent();

        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
    }*/

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check wich request we're responding to
        if (requestCode == SAVE_IDENT) {
            //Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("finish", true);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

        } else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();

            sendEmailWithImage(selectedImage);
        }
    }*/

}
